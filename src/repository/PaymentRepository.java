// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package repository;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import models.Payment;

public class PaymentRepository {
   public PaymentRepository() {
   }

   public boolean insertPayment(Payment p) {
      String sql = "INSERT INTO thanhtoan (MaDH, PTTT, SoTien, MaGiaoDich, TrangThai) VALUES (?, ?, ?, ?, ?)";

      try {
         Connection conn = DBConnection.getConnection();

         boolean var5;
         try {
            PreparedStatement ps = conn.prepareStatement(sql);

            try {
               ps.setInt(1, p.getOrderId());
               ps.setString(2, p.getMethod());
               ps.setDouble(3, p.getAmount());
               ps.setString(4, p.getTransactionId());
               ps.setString(5, p.getStatus());
               var5 = ps.executeUpdate() > 0;
            } catch (Throwable var9) {
               if (ps != null) {
                  try {
                     ps.close();
                  } catch (Throwable var8) {
                     var9.addSuppressed(var8);
                  }
               }

               throw var9;
            }

            if (ps != null) {
               ps.close();
            }
         } catch (Throwable var10) {
            if (conn != null) {
               try {
                  conn.close();
               } catch (Throwable var7) {
                  var10.addSuppressed(var7);
               }
            }

            throw var10;
         }

         if (conn != null) {
            conn.close();
         }

         return var5;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }
}
