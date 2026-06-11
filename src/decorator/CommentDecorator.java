package decorator;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class CommentDecorator extends ReviewDecorator {
   private final String reason;

   public CommentDecorator(Review review, String reason) {
      super(review);
      this.reason = reason;
   }

   public String getDisplay() {
      String var10000 = super.getDisplay();
      return var10000 + " (Lý do: " + this.reason + ")";
   }
}