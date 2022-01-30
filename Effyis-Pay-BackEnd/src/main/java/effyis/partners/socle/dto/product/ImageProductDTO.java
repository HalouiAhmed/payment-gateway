package effyis.partners.socle.dto.product;

/**
 * @author CHICHI Hamza
 */

public class ImageProductDTO {

    private Long id;
    private String url;

    public ImageProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageProductDTO(Long id, String url) {
        this.id = id;
        this.url = url;
    }


    @Override
    public String toString() {
        return "ImageProductDTO{" +
                "idImage='" + id + '\'' +
                ", UrlImage='" + url + '\'' +
                '}';
    }
}
