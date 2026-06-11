/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package decorator;

/**
 *
 * @author Admin
 */

public class HighlightedReview extends ReviewDecorator {
   public HighlightedReview(Review review) {
      super(review);
   }

   public String getDisplay() {
      return "** " + super.getDisplay() + " **";
   }
}
