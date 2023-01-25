import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import db.dao.mysql.MySqlTripDAO;
import org.junit.Test;

import javax.servlet.http.Part;
import java.sql.*;
import java.io.*;
import org.apache.commons.io.IOUtils;

public class TestDB {
    @Test
    public void testGetAll() throws Exception {
        InputStream image = new MySqlTripDAO().getAll().get(0).getPassport();
        byte[] bytes = IOUtils.toByteArray(image);
        String encoded = Base64.encode(bytes);
        String source = "data:image/jpg;base64,";
        source = source.concat(encoded.toString());
        System.out.println(source);
    }

}



















