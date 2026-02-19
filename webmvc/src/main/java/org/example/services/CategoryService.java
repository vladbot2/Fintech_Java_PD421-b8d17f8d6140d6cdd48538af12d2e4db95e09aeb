package org.example.services;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.example.dtos.category.CategoryCreateDTO;
import org.example.dtos.category.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.example.mappers.CategoryMapper;
import org.example.repositories.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ICategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileService fileService;
    private final Slugify slugify = Slugify.builder().transliterator(true).build();

    public List<CategoryItemDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public void create(CategoryCreateDTO dto) {
        var entity = new CategoryEntity();
        entity.setName(dto.getName());

        var baseSlug = slugify.slugify(dto.getName());
        var slug = baseSlug;
        int index = 1;
        while (categoryRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + index;
            index++;
        }
        entity.setSlug(slug);

        entity.setImage(fileService.load(dto.getImageUrl()));
        categoryRepository.save(entity);
    }
}
