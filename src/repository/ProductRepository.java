// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package repository;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Product;

public class ProductRepository {
   private static final ProductRepository instance = new ProductRepository();

   public ProductRepository() {
   }

   public static ProductRepository getInstance() {
      return instance;
   }

   public List<Product> getAll() {
      List<Product> list = new ArrayList();
      String sql = "    SELECT sp.MaSP, sp.TenSP, sp.Gia, sp.MoTa, sp.SL, sp.DonViTinh, sp.HinhAnh,\n           dm.Ten AS TenDanhMuc\n    FROM sanpham sp JOIN danhmuc dm ON sp.MaDanhMuc = dm.MaDanhMuc\n";

      try {
         Connection c = DBConnection.getConnection();

         try {
            PreparedStatement ps = c.prepareStatement(sql);

            try {
               ResultSet rs = ps.executeQuery();

               try {
                  while(rs.next()) {
                     Product p = new Product(rs.getString("TenSP"), rs.getString("TenDanhMuc"), rs.getDouble("Gia"), rs.getString("MoTa"), rs.getInt("SL"), rs.getString("DonViTinh"), rs.getString("HinhAnh"));
                     p.setId(rs.getInt("MaSP"));
                     list.add(p);
                  }
               } catch (Throwable var11) {
                  if (rs != null) {
                     try {
                        rs.close();
                     } catch (Throwable var10) {
                        var11.addSuppressed(var10);
                     }
                  }

                  throw var11;
               }

               if (rs != null) {
                  rs.close();
               }
            } catch (Throwable var12) {
               if (ps != null) {
                  try {
                     ps.close();
                  } catch (Throwable var9) {
                     var12.addSuppressed(var9);
                  }
               }

               throw var12;
            }

            if (ps != null) {
               ps.close();
            }
         } catch (Throwable var13) {
            if (c != null) {
               try {
                  c.close();
               } catch (Throwable var8) {
                  var13.addSuppressed(var8);
               }
            }

            throw var13;
         }

         if (c != null) {
            c.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return list;
   }

   public void add(Product p) {
      String getCat = "SELECT MaDanhMuc FROM danhmuc WHERE Ten = ?";
      String ins = "INSERT INTO sanpham (TenSP,MaDanhMuc,Gia,MoTa,SL,DonViTinh,HinhAnh) VALUES (?,?,?,?,?,?,?)";

      try {
         Connection c = DBConnection.getConnection();

         try {
            PreparedStatement ps1 = c.prepareStatement(getCat);

            try {
               ps1.setString(1, p.getCategory());
               ResultSet rs = ps1.executeQuery();
               if (!rs.next()) {
                  throw new RuntimeException("Danh mục không tồn tại!");
               }

               int iddm = rs.getInt("MaDanhMuc");
               PreparedStatement ps2 = c.prepareStatement(ins);

               try {
                  ps2.setString(1, p.getName());
                  ps2.setInt(2, iddm);
                  ps2.setDouble(3, p.getPrice());
                  ps2.setString(4, p.getDescription());
                  ps2.setInt(5, p.getQuantity());
                  ps2.setString(6, p.getBrand());
                  ps2.setString(7, p.getImage());
                  ps2.executeUpdate();
               } catch (Throwable var14) {
                  if (ps2 != null) {
                     try {
                        ps2.close();
                     } catch (Throwable var13) {
                        var14.addSuppressed(var13);
                     }
                  }

                  throw var14;
               }

               if (ps2 != null) {
                  ps2.close();
               }
            } catch (Throwable var15) {
               if (ps1 != null) {
                  try {
                     ps1.close();
                  } catch (Throwable var12) {
                     var15.addSuppressed(var12);
                  }
               }

               throw var15;
            }

            if (ps1 != null) {
               ps1.close();
            }
         } catch (Throwable var16) {
            if (c != null) {
               try {
                  c.close();
               } catch (Throwable var11) {
                  var16.addSuppressed(var11);
               }
            }

            throw var16;
         }

         if (c != null) {
            c.close();
         }
      } catch (SQLException ex) {
         ex.printStackTrace();
      }

   }

   public void remove(Product p) {
      String sql = "DELETE FROM sanpham WHERE MaSP = ?";

      try {
         Connection c = DBConnection.getConnection();

         try {
            PreparedStatement ps = c.prepareStatement(sql);

            try {
               ps.setInt(1, p.getId());
               ps.executeUpdate();
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
            if (c != null) {
               try {
                  c.close();
               } catch (Throwable var7) {
                  var10.addSuppressed(var7);
               }
            }

            throw var10;
         }

         if (c != null) {
            c.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

   }

   public void update(Product p) {
      String getMaDanhMuc = "SELECT MaDanhMuc FROM danhmuc WHERE Ten = ?";
      String sql = "    UPDATE sanpham\n    SET TenSP = ?, MaDanhMuc = ?, Gia = ?, MoTa = ?, SL = ?, DonViTinh = ?, HinhAnh = ?\n    WHERE MaSP = ?\n";

      try {
         Connection conn = DBConnection.getConnection();

         label109: {
            try {
               PreparedStatement psGet = conn.prepareStatement(getMaDanhMuc);

               label100: {
                  try {
                     psGet.setString(1, p.getCategory());
                     ResultSet rs = psGet.executeQuery();
                     if (rs.next()) {
                        int maDanhMuc = rs.getInt("MaDanhMuc");
                        PreparedStatement ps = conn.prepareStatement(sql);

                        try {
                           ps.setString(1, p.getName());
                           ps.setInt(2, maDanhMuc);
                           ps.setDouble(3, p.getPrice());
                           ps.setString(4, p.getDescription());
                           ps.setInt(5, p.getQuantity());
                           ps.setString(6, p.getBrand());
                           ps.setString(7, p.getImage());
                           ps.setInt(8, p.getId());
                           ps.executeUpdate();
                        } catch (Throwable var14) {
                           if (ps != null) {
                              try {
                                 ps.close();
                              } catch (Throwable var13) {
                                 var14.addSuppressed(var13);
                              }
                           }

                           throw var14;
                        }

                        if (ps != null) {
                           ps.close();
                        }
                        break label100;
                     }
                  } catch (Throwable var15) {
                     if (psGet != null) {
                        try {
                           psGet.close();
                        } catch (Throwable var12) {
                           var15.addSuppressed(var12);
                        }
                     }

                     throw var15;
                  }

                  if (psGet != null) {
                     psGet.close();
                  }
                  break label109;
               }

               if (psGet != null) {
                  psGet.close();
               }
            } catch (Throwable var16) {
               if (conn != null) {
                  try {
                     conn.close();
                  } catch (Throwable var11) {
                     var16.addSuppressed(var11);
                  }
               }

               throw var16;
            }

            if (conn != null) {
               conn.close();
            }

            return;
         }

         if (conn != null) {
            conn.close();
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
