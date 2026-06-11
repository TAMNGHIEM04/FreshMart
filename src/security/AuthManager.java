/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package security;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthManager {
    private static boolean isLoggedIn = false;
    private static boolean isAdmin = false;
    private static String currentUser = null;
    private static int userId = -1; // NEW: lưu MaKH sau khi đăng nhập

    public static void login(String username, String password) {
        String sql = "SELECT VaiTro FROM taikhoan WHERE SoTK = ? AND MatKhau = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                isLoggedIn = true;
                currentUser = username;
                String role = rs.getString("VaiTro");
                isAdmin = role.equalsIgnoreCase("admin");

                if (!isAdmin) {
                    // Nếu là user thì lấy MaKH từ khachhang
                    String subQuery = "SELECT MaKH FROM khachhang WHERE SoTK = ?";
                    try (PreparedStatement psKH = conn.prepareStatement(subQuery)) {
                        psKH.setString(1, username);
                        ResultSet rsKH = psKH.executeQuery();
                        if (rsKH.next()) {
                            userId = rsKH.getInt("MaKH");
                        } else {
                            // Không tìm thấy MaKH tương ứng -> xem như thất bại
                            logout();
                        }
                    }
                } else {
                    userId = -1; // admin không có MaKH
                }

            } else {
                logout(); // Đăng nhập thất bại
            }

        } catch (Exception e) {
            e.printStackTrace();
            logout();
        }
    }


    public static boolean isAdmin() {
        return isLoggedIn && isAdmin;
    }

    public static boolean isUser() {
        return isLoggedIn && !isAdmin;
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static int getUserId() {
        return userId;
    }

    public static void logout() {
        isLoggedIn = false;
        isAdmin = false;
        currentUser = null;
        userId = -1;
    }
}
