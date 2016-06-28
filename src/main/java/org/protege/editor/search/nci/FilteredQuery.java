package org.protege.editor.search.nci;

import org.protege.editor.search.lucene.QueryEvaluationException;

import org.semanticweb.owlapi.model.OWLEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Josef Hardi <johardi@stanford.edu><br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 28/06/2016
 */
public class FilteredQuery extends ComplexQuery {

    private final List<SearchPluginQuery> filters;
    private final boolean isMatchAll;

    // Not allowing external instantiation
    private FilteredQuery(List<SearchPluginQuery> filters, boolean isMatchAll) {
        this.filters = filters;
        this.isMatchAll = isMatchAll;
    }

    @Override
    public boolean isMatchAll() {
        return isMatchAll;
    }

    public List<SearchPluginQuery> getFilters() {
        return Collections.unmodifiableList(filters);
    }

    @Override
    public Set<OWLEntity> evaluate(SearchProgressListener listener) throws QueryEvaluationException {
        Set<OWLEntity> toReturn = new HashSet<>();
        for (SearchPluginQuery filter : filters) {
            Set<OWLEntity> evalResult = filter.evaluate(listener);
            if (isMatchAll) {
                NciSearchUtils.intersect(toReturn, evalResult);
            }
            else { // match any
                NciSearchUtils.union(toReturn, evalResult);
            }
        }
        return toReturn;
    }

    public static class Builder {

        private List<SearchPluginQuery> filters = new ArrayList<>();

        public Builder() {
            // NO-OP
        }

        public Builder add(SearchPluginQuery filter) {
            filters.add(filter);
            return this;
        }

        public FilteredQuery build(boolean isMatchAll) {
            return new FilteredQuery(filters, isMatchAll);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + FilteredQuery.class.getSimpleName().hashCode();
        result = prime * result + filters.hashCode();
        result = prime * result + (isMatchAll ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FilteredQuery)) {
            return false;
        }
        FilteredQuery other = (FilteredQuery) obj;
        return this.filters.equals(filters) && this.isMatchAll == other.isMatchAll;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Filters:").append(filters);
        return sb.toString();
    }
}