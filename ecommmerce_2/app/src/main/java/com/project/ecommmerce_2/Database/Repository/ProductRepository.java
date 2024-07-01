package com.project.ecommmerce_2.Database.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.project.ecommmerce_2.Database.Dao.ProductDao;
import com.project.ecommmerce_2.Database.EcommerceDatabase;
import com.project.ecommmerce_2.Model.ProductModel;

import java.util.List;

public class ProductRepository {
    public ProductDao productDao;
    private LiveData<List<ProductModel>> allProduct;

    public ProductRepository(Application application){
        EcommerceDatabase ecommerceDatabase = EcommerceDatabase.getInstance(application);
        productDao = ecommerceDatabase.productDao();
        allProduct = productDao.getProduct("");
    }

    public interface OnSearch {
        void findResult(ProductModel productModel);
        void notFound();
    }

    private static class GetProduct extends AsyncTask<Integer, Void, ProductModel> {
        OnSearch onSearch;
        ProductDao productDao;

        public GetProduct(OnSearch onSearch, ProductDao productDao) {
            this.onSearch = onSearch;
            this.productDao = productDao;
        }

        @Override
        protected ProductModel doInBackground(Integer... id) {
            return productDao.get(id[0]);
        }

        @Override
        protected void onPostExecute(ProductModel productModel) {
            if (productModel != null) {
                onSearch.findResult(productModel);
            } else {
                onSearch.notFound();
            }
        }
    }

    private static class InsertProductAll extends AsyncTask<List<ProductModel>, Void, Void> {
        private ProductDao productDao;
        private boolean truncate;

        public InsertProductAll(ProductDao productDao, boolean truncate) {
            this.productDao = productDao;
            this.truncate = truncate;
        }

        @Override
        protected Void doInBackground(List<ProductModel>... lists) {
            if (truncate) {
                productDao.deleteAll();
            }
            productDao.insertAll(lists[0]);
            return null;
        }
    }

    private static class InsertProduct extends AsyncTask<ProductModel, Void, Void> {
        private ProductDao productDao;

        public InsertProduct(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(ProductModel... productModels) {
            productDao.insert(productModels[0]);
            return null;
        }
    }

    private static class UpdateProduct extends AsyncTask<ProductModel, Void, Void> {
        ProductDao productDao;

        public UpdateProduct(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(ProductModel... productModels) {
            productDao.update(productModels[0]);
            return null;
        }
    }

    private static class DeleteProduct extends AsyncTask<ProductModel, Void, Void> {
        private ProductDao productDao;

        public DeleteProduct(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(ProductModel... productModels) {
            productDao.delete(productModels[0]);
            return null;
        }
    }

    public LiveData<List<ProductModel>> getAllProduct(String keyword) {
        return productDao.getProduct(keyword);
    }

    public LiveData<List<ProductModel>> getAllProduct() {
        return allProduct;
    }

    public void get(int id, OnSearch onSearch) {
        new GetProduct(onSearch, productDao).execute(id);
    }

    public void insertAll(List<ProductModel> data, boolean truncate) {
        new InsertProductAll(productDao, truncate).execute(data);
    }

    public void insert(ProductModel productModel) {
        new InsertProduct(productDao).execute(productModel);
    }

    public void delete(ProductModel productModel) {
        new DeleteProduct(productDao).execute(productModel);
    }

    public void update(ProductModel productModel) {
        new UpdateProduct(productDao).execute(productModel);
    }
}

