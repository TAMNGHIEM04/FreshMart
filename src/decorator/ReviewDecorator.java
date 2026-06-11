/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package decorator;

/**
 *
 * @author Admin
 */
public abstract class ReviewDecorator implements Review {
   protected final Review decoratedReview;

   public ReviewDecorator(Review review) {
      this.decoratedReview = review;
   }

   public String getDisplay() {
      return this.decoratedReview.getDisplay();
   }
}

