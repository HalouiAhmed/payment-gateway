package effyis.partners.socle.Util.CSVHelper;

import effyis.partners.socle.entity.Customer;
import effyis.partners.socle.entity.product.Product;
import org.apache.commons.csv.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CHICHI Hamza
 */

public class CSVHelper {

    public static String TYPE = "text/csv";
    static String[] CUSTUMER_HEADERs = { "nom_prenom", "email", "zip_code", "pays","ville","telephone","adresse" };

    static String[] PRODUCT_HEADERS = {"name", "price", "devise", "description", "stock"};
    public static boolean hasCSVFormat(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename()).equals("csv");
    }

    private static boolean isEmpty(CSVRecord csvRecord){
        if (null == csvRecord) return true;
        for (int i = 0; i < csvRecord.size(); i++) {
            if (StringUtils.isNotBlank(csvRecord.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static List<Product> csvToProduct(InputStream inputStream) {
        try (
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';').withIgnoreHeaderCase().withTrim());
        )
        {
            List<Product> products =new ArrayList<>();
            Iterable<CSVRecord> csvRecords =csvParser.getRecords().stream().sequential().filter(v -> !isEmpty(v)).collect(Collectors.toList());
            for (CSVRecord csvRecord:
                 csvRecords) {
                String name=csvRecord.get(PRODUCT_HEADERS[0]);
                Double price =Double.parseDouble(csvRecord.get(PRODUCT_HEADERS[1]));
                String devise = csvRecord.get(PRODUCT_HEADERS[2]);
                String description=csvRecord.get(PRODUCT_HEADERS[3]);
                int stock=Integer.getInteger(csvRecord.get(CUSTUMER_HEADERs[4]));
                Product product = new Product(name,price,devise,description,stock);
                products.add(product);
            }
            return products;
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException.getMessage());
        } catch (IOException ioException) {
            throw new RuntimeException(ioException.getMessage());
        }
    }
    public static List<Customer> csvToCustumer(InputStream is) {
        try (
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';').withIgnoreHeaderCase().withTrim());
                )
        {

            List<Customer> customers = new ArrayList<Customer>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords().stream().sequential().filter(v -> !isEmpty(v)).collect(Collectors.toList());

            for (CSVRecord csvRecord : csvRecords) {
                int cpt = 0;
                String fullName=csvRecord.get(CUSTUMER_HEADERs[cpt++]);
                String email=csvRecord.get(CUSTUMER_HEADERs[cpt++]);
                String zip_code=csvRecord.get(CUSTUMER_HEADERs[cpt++]);
                String country=csvRecord.get(CUSTUMER_HEADERs[cpt++]);
                String city=csvRecord.get(CUSTUMER_HEADERs[cpt++]);
                String phone=csvRecord.get(CUSTUMER_HEADERs[cpt++]);
                String adress=csvRecord.get(CUSTUMER_HEADERs[cpt++]);
                Customer customer = new Customer(
                        fullName,
                        email,
                        zip_code,
                        country,
                        city,
                        phone,
                        adress
                );

                customers.add(customer);
            }

            return customers;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream productsToCsv(List<Product> products) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL).withFirstRecordAsHeader().withDelimiter(';').withHeader("name","price","devise","description","quantity","stock").withTrim();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             CSVPrinter csvPrinter =new CSVPrinter(new PrintWriter(outputStream), format);) {
                 for (Product product: products){
                     List<String> data = Arrays.asList(
                             product.getName(),
                             String.valueOf(product.getPrice()),
                             product.getDevise(),
                             product.getDescription(),
                             String.valueOf(product.getQuantity()),
                             String.valueOf(product.getStock())
                     );
                     csvPrinter.printRecord(data);
                 }
                 csvPrinter.flush();
                 return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public static ByteArrayInputStream custumersToCSV(List<Customer> customers) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL).withFirstRecordAsHeader().withDelimiter(';').withHeader("fullName", "email", "codepostal","city","country","phone", "adress").withTrim();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Customer customer : customers) {
                List<String> data = Arrays.asList(
                        customer.getFullName(),
                        customer.getEmail(),
                        customer.getPostalCode(),
                        customer.getCity(),
                        customer.getCountry(),
                        customer.getPhone(),
                        customer.getAdress()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

}
