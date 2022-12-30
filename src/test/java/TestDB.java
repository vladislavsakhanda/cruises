import dao.DBManager;
import model.User;
import org.junit.Test;

import java.util.List;

public class TestDB {
    @Test
    public void testGetAll() throws Exception {
        DBManager db = new DBManager();
        List<User> users = db.getAllUsers();
//        System.out.println(users);
        users = db.findUserByLinerId(1);
        System.out.println(users);
//        DaoFactory daoFactory = new MySqlDaoFactory();//
//        List<Group> list;
//        try (Connection con = daoFactory.getConnection()) {
//            GroupDao dao = daoFactory.getGroupDao(con);
//            list = dao.getAll();
//        }
//        Assert.assertNotNull(list);
//        Assert.assertTrue(list.size() > 0);
    }
}
