package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;

// Tämä luokka koostuu niistä kyselyistä, jotak taululle Alue voidaan esittää
public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database base) {
        this.database = base;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE alue_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("alue_id");
        String nimi = rs.getString("nimi");

        Alue a = new Alue(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return a;
    }

    @Override
    public List<Alue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("alue_id");
            String nimi = rs.getString("nimi");

            alueet.add(new Alue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    public void lisaaAlue(String nimi) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Alue (nimi)VALUES (?)");
        stmt.setObject(1, nimi);
        stmt.execute();

        conn.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
