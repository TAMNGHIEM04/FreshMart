/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package decorator;

/**
 *
 * @author Admin
 */
public class StatusReview extends ReviewDecorator {
   private final String status;

   public StatusReview(Review review, String status) {
      super(review);
      this.status = status;
   }

   public String getDisplay() {
      String var10000;
      switch (this.status.toLowerCase()) {
         case "visible" -> var10000 = "[Hiển thị]";
         case "hidden" -> var10000 = "[Đã ẩn]";
         case "pending" -> var10000 = "[Chờ duyệt]";
         default -> var10000 = "[Không rõ]";
      }

      String prefix = var10000;
      return prefix + " " + super.getDisplay();
   }
}
