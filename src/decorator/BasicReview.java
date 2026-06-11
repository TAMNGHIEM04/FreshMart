/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package decorator;

/**
 *
 * @author Admin
 */
public class BasicReview implements Review {
   private final String username;
   private final String content;

   public BasicReview(String username, String content) {
      this.username = username;
      this.content = content;
   }

   public String getDisplay() {
      return this.username + " - " + this.content;
   }
}
