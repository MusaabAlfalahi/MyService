package net.shahid.microservices.myservice.domain.mapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.shahid.mbc.api.gen.model.ProductDto;
import net.shahid.mbc.api.gen.model.ProductRequest;
import net.shahid.microservices.myservice.domain.entity.Localization;
import net.shahid.microservices.myservice.domain.entity.Product;
import net.shahid.microservices.myservice.domain.entity.UserContext;
import net.shahid.microservices.myservice.domain.enums.Type;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductMapper {
    ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product mapFromRequest(ProductRequest productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        product.setTitle(Localization.builder()
                .ar(productRequest.getTitleAr())
                .en(productRequest.getTitleEn())
                .fr(productRequest.getTitleFr())
                .build());
        product.setType(Type.getByName(productRequest.getType()));
        return product;
    }

    public ProductDto mapToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setTitle(product.getTitle(UserContext.getUserLanguage()));
        return productDto;
    }
}
