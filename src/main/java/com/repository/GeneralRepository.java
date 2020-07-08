package com.repository;

import com.googlecode.jmapper.JMapper;
import com.model.BaseEntity;
import com.model.BaseEntityView;
import com.service.search.SearchCriteria;
import com.service.search.SearchCriteriaList;
import com.service.search.SearchSpecification;
import com.view.SimplePageResponse;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@NoRepositoryBean
public interface GeneralRepository<M extends BaseEntity, V extends BaseEntityView, PK extends Serializable> extends JpaRepository<M, PK>, JpaSpecificationExecutor<M> {

    //Page<M> findAll(Specification<M> var1, Pageable var2);

/*    default M saveModel(M m) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap hashMap = objectMapper.convertValue(m, HashMap.class);
        Long id = Long.valueOf(hashMap.get("id").toString());
        if (id > 0) {
            m.changeDate = new Date();
        }
        return save(m);
    }*/

    default SimplePageResponse<M> findAllCriteria(SearchCriteriaList search) {
        SimplePageResponse<M> temp = new SimplePageResponse<>();
        Specification<M> combinedSpecs = creatFilter(search.getSearch());

        Page results = findAll(combinedSpecs, paging(search));
        List<M> views = results.getContent();
        temp.setContent(views);
        temp.setCount(results.getTotalElements());
        return temp;
    }


    default Specification<M> creatFilter(HashSet<SearchCriteria> search) {
        final Specification<M>[] combinedSpecs = new Specification[]{null};
        search.stream().filter(item -> item.getValue() != null).forEach(item -> {
            if (item.getKey().contains(".")) {
                combinedSpecs[0] = Specification.where(
                        (root, query, builder) -> {
                            String[] relation = item.getKey().split("\\.");
                            //final Join<M, B> join = root.join(item.getKey().split("\\.")[0]);
                            if (relation.length == 2) {
                                return builder.or(
                                        builder.like(root.join(relation[0]).get(relation[1]), item.getValue().toString())
                                );
                            } else {
                                return builder.or(
                                        builder.like(root.join(relation[0]).join(relation[1]).get(relation[2]), item.getValue().toString())
                                );
                            }
                        });
            } else {
                SearchSpecification newItem = new SearchSpecification(new SearchCriteria(item.getKey(), item.getOperation(), item.getValue()));
                combinedSpecs[0] = combinedSpecs[0] == null ? Specification.where(newItem) : combinedSpecs[0].and(newItem);
            }
        });
        return combinedSpecs[0];
    }

    default SimplePageResponse<V> list(M t, JMapper<V, M> mapperToView, SearchCriteriaList search) {
        Example<M> example = Example.of(t);
        Page results = findAll(example, paging(search));

        List<M> modelList = results.getContent();
        List<V> viewList = new ArrayList<>();
        modelList.forEach(item ->
                viewList.add(mapperToView.getDestination(item))
        );
        return new SimplePageResponse<V>(viewList, results.getTotalElements());
    }

    default SimplePageResponse<M> list(M m, SearchCriteriaList search) {
        Example<M> example = Example.of(m);
        Page results = findAll(example, paging(search));

        List<M> modelList = results.getContent();
        return new SimplePageResponse<M>(modelList, results.getTotalElements());
    }

    default Pageable paging(SearchCriteriaList search) {
        Sort.Direction direction;
        if (search.getSort().contains("desc")) {
            direction = Sort.Direction.DESC;
            search.setSort(search.getSort().replace("desc", ""));
        } else {
            direction = Sort.Direction.ASC;
            search.setSort(search.getSort().replace("asc", ""));
        }
        return PageRequest.of(search.getPage() - 1, search.getSize(), new Sort(direction, search.getSort().trim()));
    }

}
