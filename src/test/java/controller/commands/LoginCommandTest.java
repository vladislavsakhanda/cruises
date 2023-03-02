package controller.commands;

import controller.FrontCommand;
import controller.FrontControllerServlet;
import db.dao.PBKDF2;
import db.dao.RoleHasUserDAO;
import db.dao.UserDAO;
import db.dao.mysql.MySqlRoleHasUserDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Role;
import db.dao.mysql.entity.RoleHasUser;
import db.dao.mysql.entity.User;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import services.RoleHasUserService;
import services.ServiceFactory;
import services.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoginCommandTest {

    @Mock
    FrontControllerServlet frontControllerServlet;
    @Mock
    FrontCommand frontCommand;

    @Mock
    ServletContext context;
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher dispatcher;

    @Spy
    private LoginCommand command;


    private static final String VALID_EMAIL = "test@ukr.net";
    private static final String VALID_PASSWORD = "123456";

    private static final String INVALID_EMAIL = "failEmail";
    private static final String INVALID_PASSWORD = "";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        command.init(context, request, response);

        when(request.getSession()).thenReturn(session);
        when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }


    @Test
    public void processTest() throws ServletException, IllegalFieldException, SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        doNothing().when(command).doGet();
        doNothing().when(command).doPost();

        when(request.getAttribute("method")).thenReturn("GET");
        command.process();
        verify(command, Mockito.times(1)).doGet();

        when(request.getAttribute("method")).thenReturn("POST");
        command.process();
        verify(command, Mockito.times(1)).doPost();
    }

    @Test
    public void doGetUserNotLoggedInTest() throws ServletException, IllegalFieldException, SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        when(session.getAttribute("userEmail")).thenReturn(null);
        String email = VALID_EMAIL;
        when(request.getParameter("email")).thenReturn(email);

        command.doGet();

        verify(request).setAttribute("email", email);
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    public void doGetUserLoggedInTest() throws Exception {
        when(session.getAttribute("userEmail")).thenReturn(VALID_EMAIL);

        command.doGet();

        verify(response).sendRedirect(anyString());
        verify(request, never()).setAttribute(anyString(), any());
        verify(context, never()).getRequestDispatcher(anyString());
    }


    @Test
    void testDoPostSuccessfulLogin()
            throws ServletException, IOException, DBException, IllegalFieldException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        User testUser = User.createUser(
                10, "test", "test",
                VALID_EMAIL, VALID_PASSWORD
        );
        RoleHasUser testRoleHasUser = new RoleHasUser(
                testUser.getId(), Role.Roles.ADMIN.getCode()
        );
        when(request.getParameter("email")).thenReturn(testUser.getEmail());
        when(request.getParameter("password")).thenReturn(testUser.getPassword());
        doNothing().when(command).doGet();


        ServiceFactory serviceFactory = mock(ServiceFactory.class);
        UserService userService = mock(UserService.class);
        RoleHasUserService roleHasUserService = mock(RoleHasUserService.class);
        UserDAO mySqlUserDAO = mock(MySqlUserDAO.class);
        RoleHasUserDAO roleHasUserDAO = mock(MySqlRoleHasUserDAO.class);
        mockStatic(PBKDF2.class);
        when(PBKDF2.validatePassword(anyString(), anyString())).thenReturn(true);
        try (MockedStatic<ServiceFactory> serviceFactoryMock = mockStatic(ServiceFactory.class);
             MockedStatic<MySqlUserDAO> userDaoMock = mockStatic(MySqlUserDAO.class);
             MockedStatic<MySqlRoleHasUserDAO> roleHasUserDAOMock = mockStatic(MySqlRoleHasUserDAO.class)) {
            serviceFactoryMock.when(ServiceFactory::getServiceFactory).thenReturn(serviceFactory);
            userDaoMock.when(MySqlUserDAO::getInstance).thenReturn(mySqlUserDAO);
            roleHasUserDAOMock.when(MySqlRoleHasUserDAO::getInstance).thenReturn(roleHasUserDAO);

            when(serviceFactory.getUserService(any(UserDAO.class))).thenReturn(userService);
            when(serviceFactory.getRoleHasUserService(any(RoleHasUserDAO.class))).thenReturn(roleHasUserService);
            when(userService.read(anyString())).thenReturn(testUser);

            // When user have an admin role
            when(roleHasUserService.read(Role.Roles.ADMIN.getCode(), testUser.getId())).thenReturn(null);
            command.doPost();

            // When user doesn't have an admin role
            when(roleHasUserService.read(Role.Roles.ADMIN.getCode(), testUser.getId())).thenReturn(testRoleHasUser);
            command.doPost();

            verify(request, Mockito.times(0)).
                    setAttribute(eq("messageEmail"), anyString());
            verify(request, Mockito.times(0)).
                    setAttribute(eq("messagePassword"), anyString());
            verify(session).setAttribute("role", "admin");
            verify(session, Mockito.times(2)).setAttribute("userId", testUser.getId());
            verify(session, Mockito.times(2)).setAttribute("userName", testUser.getName());
            verify(session, Mockito.times(2)).setAttribute("userSurname", testUser.getSurname());
            verify(session, Mockito.times(2)).setAttribute("userEmail", testUser.getEmail());

            verify(command, Mockito.times(2)).doGet();
        }
    }

    @Test
    void testDoPostFailureLogin()
            throws ServletException, IOException, DBException, IllegalFieldException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        when(request.getParameter("email")).thenReturn(INVALID_EMAIL);
        when(request.getParameter("password")).thenReturn(INVALID_PASSWORD);
        doNothing().when(command).doGet();

        ServiceFactory serviceFactory = mock(ServiceFactory.class);
        UserService userService = mock(UserService.class);
        RoleHasUserService roleHasUserService = mock(RoleHasUserService.class);
        UserDAO mySqlUserDAO = mock(MySqlUserDAO.class);
        RoleHasUserDAO roleHasUserDAO = mock(MySqlRoleHasUserDAO.class);

        when(PBKDF2.validatePassword(anyString(), anyString())).thenReturn(true);
        try (MockedStatic<ServiceFactory> serviceFactoryMock = mockStatic(ServiceFactory.class);
             MockedStatic<MySqlUserDAO> userDaoMock = mockStatic(MySqlUserDAO.class);
             MockedStatic<MySqlRoleHasUserDAO> roleHasUserDAOMock = mockStatic(MySqlRoleHasUserDAO.class)) {
            serviceFactoryMock.when(ServiceFactory::getServiceFactory).thenReturn(serviceFactory);
            userDaoMock.when(MySqlUserDAO::getInstance).thenReturn(mySqlUserDAO);
            roleHasUserDAOMock.when(MySqlRoleHasUserDAO::getInstance).thenReturn(roleHasUserDAO);


            when(serviceFactory.getUserService(any(UserDAO.class))).thenReturn(userService);
            when(serviceFactory.getRoleHasUserService(any(RoleHasUserDAO.class))).thenReturn(roleHasUserService);
            when(userService.read(anyString())).thenReturn(null);

            command.doPost();

            verify(request, Mockito.times(1)).
                    setAttribute(eq("messageEmail"), anyString());
            verify(request, Mockito.times(1)).
                    setAttribute(eq("messagePassword"), anyString());

            verify(session, Mockito.times(0)).setAttribute(eq("role"), eq("admin"));
            verify(session, Mockito.times(0)).
                    setAttribute(eq("userId"), anyLong());
            verify(session, Mockito.times(0)).
                    setAttribute(eq("userName"), anyString());
            verify(session, Mockito.times(0)).
                    setAttribute(eq("userSurname"), anyString());
            verify(session, Mockito.times(0)).
                    setAttribute(eq("userEmail"), anyString());

            verify(command, Mockito.times(1)).doGet();
        }
    }
}











