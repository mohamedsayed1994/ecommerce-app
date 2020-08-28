package com.test.ecommerceapp.configuration;

import com.test.ecommerceapp.entity.Country;
import com.test.ecommerceapp.entity.Product;
import com.test.ecommerceapp.entity.ProductCategory;
import com.test.ecommerceapp.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    //expose rest api to show entity ids
    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //expose rest api to show entity ids
    private void exposeId(RepositoryRestConfiguration config) {
        //get a list of entity class from entity manger
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        //create array of entity type
        List<Class> entityClass = new ArrayList<>();
        //get entity type for entities
        for (EntityType temp : entities) {
            entityClass.add(temp.getJavaType());
        }
        //expose entity ids for array of entity type
        Class[] domainType = entityClass.toArray(new Class[0]);
        config.exposeIdsFor(domainType);

    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        //expose rest api to show entity ids
        exposeId(config);

        // disable some action to make api read only
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
        // disable some put post delete for product category
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
        // disable some put post delete for product
        disableHttpMethods(Product.class, config, theUnsupportedActions);
        // disable some put post delete for country and state
        disableHttpMethods(Country.class, config, theUnsupportedActions);
        disableHttpMethods(State.class, config, theUnsupportedActions);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
    }
}
