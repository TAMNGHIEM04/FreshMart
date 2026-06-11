/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package repository;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Review;

public class ReviewRepository {
   private final Connection conn = DBConnection.getConnection();

   public ReviewRepository() {
   }

   public List<Review> getAllReviews() {
      List<Review> list = new ArrayList();
      String sql = "SELECT * FROM DanhGia";

      try {
         PreparedStatement st = this.conn.prepareStatement(sql);

         try {
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
               list.add(new Review(rs.getInt("MaDG"), rs.getInt("MaKH"), rs.getInt("MaSP"), rs.getString("DanhGia"), rs.getString("BinhLuan"), rs.getString("TrangThai")));
            }
         } catch (Throwable var7) {
            if (st != null) {
               try {
                  st.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (st != null) {
            st.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return list;
   }

   public List<Review> getReviewsByStatus(String status) {
      List<Review> list = new ArrayList();
      String sql = "SELECT * FROM DanhGia WHERE TrangThai = ?";

      try {
         PreparedStatement st = this.conn.prepareStatement(sql);

         try {
            st.setString(1, status);
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
               list.add(new Review(rs.getInt("MaDG"), rs.getInt("MaKH"), rs.getInt("MaSP"), rs.getString("DanhGia"), rs.getString("BinhLuan"), rs.getString("TrangThai")));
            }
         } catch (Throwable var8) {
            if (st != null) {
               try {
                  st.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (st != null) {
            st.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return list;
   }

   public void updateStatus(int maDG, String newStatus) {
      String sql = "UPDATE DanhGia SET TrangThai = ? WHERE MaDG = ?";

      try {
         PreparedStatement st = this.conn.prepareStatement(sql);

         try {
            st.setString(1, newStatus);
            st.setInt(2, maDG);
            st.executeUpdate();
         } catch (Throwable var8) {
            if (st != null) {
               try {
                  st.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (st != null) {
            st.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

   }

   public Map<Integer, Integer> countReviewsByProduct() {
      Map<Integer, Integer> result = new HashMap();
      String sql = "SELECT MaSP, COUNT(*) AS SoDanhGia FROM DanhGia GROUP BY MaSP";

      try {
         PreparedStatement st = this.conn.prepareStatement(sql);

         try {
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
               result.put(rs.getInt("MaSP"), rs.getInt("SoDanhGia"));
            }
         } catch (Throwable var7) {
            if (st != null) {
               try {
                  st.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (st != null) {
            st.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return result;
   }

   public Map<String, Integer> countReviewsByStatus() {
      Map<String, Integer> result = new HashMap();
      String sql = "SELECT TrangThai, COUNT(*) AS SoLuong FROM DanhGia GROUP BY TrangThai";

      try {
         PreparedStatement st = this.conn.prepareStatement(sql);

         try {
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
               result.put(rs.getString("TrangThai"), rs.getInt("SoLuong"));
            }
         } catch (Throwable var7) {
            if (st != null) {
               try {
                  st.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (st != null) {
            st.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return result;
   }

   public List<Review> getReviewsByProduct(int productId) {
      List<Review> list = new ArrayList();
      String sql = "SELECT * FROM DanhGia WHERE MaSP = ?";

      try {
         PreparedStatement st = this.conn.prepareStatement(sql);

         try {
            st.setInt(1, productId);
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
               list.add(new Review(rs.getInt("MaDG"), rs.getInt("MaKH"), rs.getInt("MaSP"), rs.getString("danhGia"), rs.getString("BinhLuan"), rs.getString("TrangThai")));
            }
         } catch (Throwable var8) {
            if (st != null) {
               try {
                  st.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (st != null) {
            st.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return list;
   }
}

