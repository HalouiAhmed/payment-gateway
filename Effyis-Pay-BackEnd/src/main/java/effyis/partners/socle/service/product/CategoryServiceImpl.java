package effyis.partners.socle.service.product;

import com.cloudinary.Cloudinary;
import effyis.partners.socle.dto.product.CategoryDTO;
import effyis.partners.socle.dto.product.request.CategorySaveRequest;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;
import effyis.partners.socle.entity.product.Category;
import effyis.partners.socle.enums.TypeAttachement;
import effyis.partners.socle.exception.ElementNotFoundException;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.cloudinary.CloudinaryInformationRepository;
import effyis.partners.socle.repository.product.CategoryReposiroty;
import effyis.partners.socle.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author CHICHI Hamza
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryReposiroty categoryReposiroty;

    @Autowired
    CloudinaryInformationRepository cloudinaryInformationRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<CategoryDTO> getCategories() {
        Account account = this.getConnectedAccount();

        List <Category> categories = categoryReposiroty.findByAccount_Id(account.getId());
        List <CategoryDTO> categoryResponse = new ArrayList<>();
        categories.forEach(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setCategoryImage(category.getCategoryImage().getSecure_url()); //Get url from cloudinary table
            categoryDTO.setCategoryName(category.getCategoryName());
            categoryResponse.add(categoryDTO);
        });
        return categoryResponse;
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Account account = this.getConnectedAccount();


        Category category = categoryReposiroty.findByIdAndAccount_Id(id,account.getId())
                .orElseThrow(() -> new ElementNotFoundException("category with id : " + id));
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryImage(category.getCategoryImage().getSecure_url()); //Get url from cloudinary table
        categoryDTO.setCategoryName(category.getCategoryName());
        return categoryDTO;
    }

    @Override
    public void saveCategory(CategorySaveRequest categorySaveRequest) {
        Account account = this.getConnectedAccount();

        Category category = new Category();

        CloudinaryInformation imageCategory = cloudinaryInformationRepository.findById(categorySaveRequest.getCategoryImage())
                .orElseThrow(() -> new ElementNotFoundException("image with id " + categorySaveRequest.getCategoryImage()));

        if(!imageCategory.getAttachement().getTypeAttachement().name().equals(TypeAttachement.CATEGORY.name())){
            throw  new ElementNotFoundException("image with id " + categorySaveRequest.getCategoryImage() + " of type category");
        }

        Category categoryTest = categoryReposiroty.findByCategoryImage(imageCategory).orElse(null);

        if (categoryTest == null){
            category.setCategoryImage(imageCategory);
            category.setCategoryName(categorySaveRequest.getCategoryName());
            category.setAccount(account);
            categoryReposiroty.save(category);
        }
        else{
            throw  new ElementNotFoundException("image with id " + categorySaveRequest.getCategoryImage()  + " already taken");
        }

    }

    @Override
    public void deleteCategory(Long id) throws IOException {
        Account account = this.getConnectedAccount();

        Category category = categoryReposiroty.findByIdAndAccount_Id(id,account.getId())
                .orElseThrow(() -> new ElementNotFoundException("category with id : " + id));

        System.err.println(category.getCategoryName());
        System.err.println(category.getCategoryImage().getPublic_id());


        cloudinary.uploader().destroy(category.getCategoryImage().getPublic_id(), new HashMap());

        categoryReposiroty.deleteById(id);
    }

    @Override
    @Transactional
    public void editCategoryName(Long id, String categoryName) {
        Account account = this.getConnectedAccount();

        Category category = categoryReposiroty.findByIdAndAccount_Id(id,account.getId())
                .orElseThrow(
                () -> new ElementNotFoundException("product with id " + id));

        if (!Objects.equals(category.getCategoryName(), categoryName)){
            category.setCategoryName(categoryName);
        }
    }

    @Override
    @Transactional
    public void editCategoryImage(Long id, Long idImage) throws IOException {
        Account account = this.getConnectedAccount();

        Category category = categoryReposiroty.findByIdAndAccount_Id(id,account.getId())
                .orElseThrow(
                        () -> new ElementNotFoundException("product with id " + id));

        CloudinaryInformation categoryImage = category.getCategoryImage();

        CloudinaryInformation cloudinaryInformationTemp =  cloudinaryInformationRepository.findById(idImage)
                .orElseThrow(() -> new ElementNotFoundException("image with id " + idImage));

        Category categoryTest = categoryReposiroty.findByCategoryImage(cloudinaryInformationTemp).orElse(null);

        if (categoryTest != null){
            throw  new ElementNotFoundException("image with id " + idImage  + " already taken");
        }

        if (!cloudinaryInformationTemp.getId().equals(categoryImage.getId())){
            Long idToRemove = categoryImage.getId();
            category.setCategoryImage(cloudinaryInformationTemp);
            //categoryReposiroty.save(category);

            CloudinaryInformation cloudinaryInformationsToRemove = cloudinaryInformationRepository.findById(idToRemove)
                    .orElseThrow(() -> new ElementNotFoundException("image with id " + idToRemove));
            cloudinary.uploader().destroy(cloudinaryInformationsToRemove.getPublic_id(), new HashMap()); //Remove images from cloudinar

            cloudinaryInformationRepository.deleteById(idToRemove);
        }

    }

    private Account getConnectedAccount(){
        String email = accountService.getConnectedUser();
        return accountRepository.findByLogin(email).orElseThrow(
                () -> new ElementNotFoundException("account"));
    }

}
