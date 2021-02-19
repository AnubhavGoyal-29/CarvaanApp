package startup.carvaan.myapplication.ui.about.dailogFragments;

public class commentsModal {
    String username;
    String comment;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public commentsModal() {
    }

    public commentsModal(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }
}
