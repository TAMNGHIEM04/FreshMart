// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
   private static final String URL = "jdbc:mysql://localhost:3306/homefood";
   private static final String USER = "root";
   private static final String PASSWORD = "";

   public DBConnection() {
   }

   public static Connection getConnection() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         return DriverManager.getConnection("jdbc:mysql://localhost:3306/homefood", "root", "");
      } catch (Exception ex) {
         ex.printStackTrace();
         return null;
      }
   }
}
