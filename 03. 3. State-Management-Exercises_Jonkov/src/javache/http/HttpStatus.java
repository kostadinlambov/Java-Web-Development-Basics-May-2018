package javache.http;

public enum HttpStatus {
    Ok("200 OK"),
    Created("201 Created"),
    NoContent("204 No Content"),
    SeeOther("303 See Other"),
    BadRequest("400 Bad Request"),
    Unauthorized("401 Unauthorized"),
    Forbidden("403 Forbidden"),
    NotFound("404 Not Found"),
    InternalServerError("500 Internal Server Error");

    private String statusPhrase;

    HttpStatus(String statusPhrase) {
        this.setStatusPhrase(statusPhrase);
    }

    public int getStatusCode() { return Integer.parseInt(this.statusPhrase.split(" ")[0]);}

    public String getStatusPhrase() {
        return this.statusPhrase;
    }

    private void setStatusPhrase(String statusPhrase) {
        this.statusPhrase = statusPhrase;
    }
}
