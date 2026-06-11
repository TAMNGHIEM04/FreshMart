package adapter;

public class GoogleReviewAdapter implements ExternalRatingSystem {
    private GoogleReview google = new GoogleReview();
    public void sendReview(String text) { google.post(text); }
}