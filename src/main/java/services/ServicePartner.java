package services;

import models.Partner;
import tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePartner {
    private Connection cnx;

    public ServicePartner() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public void ajouter(Partner partner) throws SQLException {
        String sqlUser = "INSERT INTO user(firstname, lastname, email, password, joinDate, avatar) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement steUser = cnx.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
        steUser.setString(1, partner.getFirstname());
        steUser.setString(2, partner.getLastname());
        steUser.setString(3, partner.getEmail());
        steUser.setString(4, partner.getPassword());
        steUser.setDate(5, new java.sql.Date(partner.getJoinDate().getTime()));
        steUser.setString(6, partner.getAvatar());
        steUser.executeUpdate();

        ResultSet rs = steUser.getGeneratedKeys();
        if (rs.next()) {
            int userId = rs.getInt(1);

            String sqlPartner = "INSERT INTO partner(id, organizationName) VALUES(?, ?)";
            PreparedStatement stePartner = cnx.prepareStatement(sqlPartner);
            stePartner.setInt(1, userId);
            stePartner.setString(2, partner.getOrganizationName());
            stePartner.executeUpdate();

            System.out.println("Partner ajouté avec ID : " + userId);
        }
    }

    public void modifier(int id, String organizationName) throws SQLException {
        String sql = "UPDATE partner SET organizationName = ? WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setString(1, organizationName);
        st.setInt(2, id);
        int rowsUpdated = st.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Partner modifié avec succès !");
        } else {
            System.out.println("Aucun partenaire trouvé avec cet ID.");
        }
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setInt(1, id);
        int rowsDeleted = st.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Partner supprimé avec succès !");
        } else {
            System.out.println("Aucun partenaire trouvé avec cet ID.");
        }
    }

    public List<Partner> recuperer() throws SQLException {
        String sql = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, u.joinDate, u.avatar, p.organizationName FROM user u INNER JOIN partner p ON u.id = p.id";
        Statement ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery(sql);

        List<Partner> partners = new ArrayList<>();
        while (rs.next()) {
            Partner p = new Partner();
            p.setId(rs.getInt("id"));
            p.setFirstname(rs.getString("firstname"));
            p.setLastname(rs.getString("lastname"));
            p.setEmail(rs.getString("email"));
            p.setPassword(rs.getString("password"));
            p.setJoinDate(rs.getDate("joinDate"));
            p.setAvatar(rs.getString("avatar"));
            p.setOrganizationName(rs.getString("organizationName"));
            partners.add(p);
        }
        return partners;
    }
}
