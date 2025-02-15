package tests;

import models.Partner;
import services.ServicePartner;
import tools.MyDataBase;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyDataBase md = MyDataBase.getInstance();

        try {
            ServicePartner servicePartner = new ServicePartner();
            Partner partner = new Partner(
                    1,
                    "islem",
                    "raissi",
                    "islemraissi789@gmail.Com",
                    "islem123",
                    new Date(),
                    "avatar.jpg",
                    "esprit"
            );
            servicePartner.ajouter(partner);
            servicePartner.modifier(1, "EY");
            List<Partner> partners = servicePartner.recuperer();
            for (Partner p : partners) {
                System.out.println(p);
            }
           // servicePartner.supprimer(1);

        } catch (SQLException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
