package controller.commands;

import controller.FrontCommand;
import controller.FrontControllerServlet;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CruisesCatalogCommandTest {
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
    private CruisesCatalogCommand command;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        command.init(context, request, response);

        when(request.getSession()).thenReturn(session);
        when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void processTest() throws ServletException, IllegalFieldException, SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, DBException {
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
    public void doPostTest() throws ServletException, IOException, IllegalFieldException, DBException, SQLException {
        command.doPost();
        verify(command, Mockito.times(1)).doGet();
    }
}
