package effyis.partners.socle.enums;

public enum BucketName {
	

	    FACTURE_PDF("billstorage");

	    private final String bucketName;

	    BucketName(String bucketName) {
	        this.bucketName = bucketName;
	    }

	    public String getBucketName() {
	        return bucketName;
	    
	}
}
