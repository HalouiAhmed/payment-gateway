package effyis.partners.socle;




import effyis.partners.socle.entity.Role;
import effyis.partners.socle.entity.cloudinary.Attachement;

import effyis.partners.socle.enums.TypeAttachement;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.RoleRepository;
import effyis.partners.socle.repository.UserRepository;
import effyis.partners.socle.repository.cloudinary.AttachementRepository;
import effyis.partners.socle.repository.cloudinary.CloudinaryInformationRepository;
import effyis.partners.socle.repository.product.CategoryReposiroty;
import effyis.partners.socle.repository.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;



/**
 *
 * @author EL KOTB ZAKARIA
 *
 */
@SpringBootApplication
@EnableWebSecurity
@EnableAsync
public class SocleEffyisApplication implements CommandLineRunner ,  RepositoryRestConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SocleEffyisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


	}


	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository,
										AccountRepository accountRepository,
										UserRepository userRepository,
										AttachementRepository attachementRepository,
										CloudinaryInformationRepository cloudinaryInformationRepository,
										CategoryReposiroty categoryReposiroty,
										ProductRepository productRepository){
		return args -> {

			Role admin = new Role("ADMIN");
			Role super_admin = new Role("SUPER_ADMIN");
			if(!roleRepository.existsById(1L) && !roleRepository.existsById(2L) ){
				roleRepository.save(admin);
				roleRepository.save(super_admin);
			}


			Attachement product_attachement = new Attachement(TypeAttachement.PRODUCT);
			Attachement category_attachement = new Attachement(TypeAttachement.CATEGORY);
			Attachement logo_attachement = new Attachement(TypeAttachement.LOGO);
			if(!attachementRepository.existsById(1L) && !attachementRepository.existsById(2L)){
				attachementRepository.save(product_attachement);
				attachementRepository.save(category_attachement);
			}
			attachementRepository.save(logo_attachement);

		};
	}
}
