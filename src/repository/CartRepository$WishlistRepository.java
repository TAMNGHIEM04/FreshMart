// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package repository;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRepository$WishlistRepository {
   private final Connection conn = DBConnection.getConnection();

   public CartRepository$WishlistRepository(final CartRepository this$0) {
   }

   public boolean addToWishlist(int userId, int productId) {
      try {
         PreparedStatement check = this.conn.prepareStatement("SELECT * FROM YeuThich WHERE MaKH = ? AND MaSP = ?");

         boolean var13;
         label69: {
            boolean var6;
            try {
               check.setInt(1, userId);
               check.setInt(2, productId);
               ResultSet rs = check.executeQuery();
               if (rs.next()) {
                  var13 = false;
                  break label69;
               }

               PreparedStatement insert = this.conn.prepareStatement("INSERT INTO YeuThich(MaKH, MaSP) VALUES (?, ?)");

               try {
                  insert.setInt(1, userId);
                  insert.setInt(2, productId);
                  insert.executeUpdate();
                  var6 = true;
               } catch (Throwable var10) {
                  if (insert != null) {
                     try {
                        insert.close();
                     } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                     }
                  }

                  throw var10;
               }

               if (insert != null) {
                  insert.close();
               }
            } catch (Throwable var11) {
               if (check != null) {
                  try {
                     check.close();
                  } catch (Throwable var8) {
                     var11.addSuppressed(var8);
                  }
               }

               throw var11;
            }

            if (check != null) {
               check.close();
            }

            return var6;
         }

         if (check != null) {
            check.close();
         }

         return var13;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }
}
