package dlugolecki.pawel.service.serviceImpl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Category;
import dlugolecki.pawel.repository.impl.CategoryRepositoryImpl;
import dlugolecki.pawel.repository.repos.CategoryRepository;
import dlugolecki.pawel.service.ServiceHelpers;
import dlugolecki.pawel.service.Syntax;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CategoryImpl {
    private ServiceHelpers service = new ServiceHelpers();
    private Syntax syntax = new Syntax();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private Scanner scanner = new Scanner(System.in);

    public void addCategory() {
        try {
            System.out.println("Enter: CATEGORY NAME");
            String categoryName = scanner.nextLine();
            if (syntax.trueCategoryName(categoryName)) {
                throw new IllegalArgumentException("WRONG CATEGORY NAME");
            }
            if (service.doesCategoryWithNameExist(categoryName)) {
                throw new IllegalArgumentException("DUPLICATE CATEGORY");
            }
            Optional<Category> category = categoryRepository.findOneByName(categoryName);
            categoryRepository.add
                    (Category
                            .builder()
                            .name(categoryName)
                            .build());
            System.out.println("THE CATEGORY HAS BEEN ADDED CORRECTLY: " + categoryName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE: ADD CATEGORY: ", LocalDateTime.now());
        }
    }

    public void deleteOneCategory() {
        try {
            System.out.println("Enter: CATEGORY ID");
            Integer id = scanner.nextInt();

            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG CATEGORY ID");
            }
            if (!service.doesCategoryWithIdExist(id)) {
                throw new NullPointerException("CATEGORY WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Category> categoryOp = categoryRepository
                    .findOneById(id);
            categoryRepository.delete(id);
            System.out.println("CATEGORY WITH ID " + id + " HAS BEEN DELETE");

        } catch (Exception e) {
            throw new MyException("SERVICE: DELETE ONE CATEGORY: ", LocalDateTime.now());
        }
    }

    public void deleteAllCategory() {
        for (Category c : categoryRepository.findAll()) {
            categoryRepository.delete(c.getId());
        }
        System.out.println("SERVICE: ALL CATEGORY HAS BEEN DELETE");
    }


    public void updateCategory() {
        try {
            System.out.println("Enter: CATEGORY NAME");
            String categoryName = scanner.nextLine();
            if (syntax.trueCategoryName(categoryName)) {
                throw new IllegalArgumentException("WRONG CATEGORY NAME");
            }
            if (!service.doesCategoryWithNameExist(categoryName)) {
                throw new IllegalArgumentException("CATEGORY WITH GIVEN NAME DOES NOT EXIST");
            }

            System.out.println("Enter: CATEGORY NEW NAME");
            String categoryNewName = scanner.nextLine();
            if (syntax.trueCategoryName(categoryNewName)) {
                throw new IllegalArgumentException("WRONG CATEGORY NEW NAME");
            }
            if (service.doesCategoryWithNameExist(categoryNewName)) {
                throw new IllegalArgumentException("DUPLICATE CATEGORY");
            }
            Category category = categoryRepository
                    .findOneByName(categoryName)
                    .orElseThrow(NullPointerException::new);
            category.setName(categoryNewName);
            categoryRepository.update(category);
            System.out.println("THE CATEGORY HAS BEEN UPDATDED CORRECTLY: " + "FROM: " + categoryName + " TO: "+ categoryNewName);

        } catch (Exception e) {
            throw new MyException("SERVICE: UPDATE CATEGORY: ", LocalDateTime.now());
        }
    }

    public Category findOneCategory() {
        try {
            System.out.println("Enter: CATEGORY ID");
            Integer id = scanner.nextInt();
            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG CATEGORY ID");
            }
            if (!service.doesCategoryWithIdExist(id)) {
                throw new NullPointerException("CATEGORY WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Category> categoryOp = categoryRepository.findOneById(id);
            System.out.println(categoryOp);
            System.out.println("THE CATEGORY WITH GIVEN ID HAS BEEN FOUND:");

        } catch (Exception e) {
            throw new MyException("SERVICE: FIND ONE CATEGORY: ", LocalDateTime.now());
        }
        return null;
    }

    public List<Category> findAllCategory () {
        try {
            System.out.println("ALL CATEGORIES");
            List<Category> categories = categoryRepository.findAll();

            if (categories == null || categories.isEmpty()) {
                throw new NullPointerException("CATEGORY IS EMPTY");
            }

            categories.forEach(s -> System.out.println(s));
        } catch (Exception e) {
            throw new MyException("SERVICE: FIND ALL CATEGORY: ", LocalDateTime.now());
        }
        return  null;
    }
}
