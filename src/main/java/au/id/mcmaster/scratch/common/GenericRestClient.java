package au.id.mcmaster.scratch.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import au.id.mcmaster.scratch.product.Product;

public abstract class GenericRestClient<T extends Object>
{
    private String controllerUri;
    private Client client;
    
    protected GenericRestClient(String baseUri, String basePath)
    {
        this.controllerUri = baseUri + basePath;
        ClientConfig cc = new DefaultClientConfig();
        cc.getClasses().add(PageImpl.class);
        cc.getClasses().add(Page.class);
        this.client = Client.create(cc);
    }

    public ClientSidePage<T> list()
    {
        ParameterizedType genericType = new ParameterizedType() { 
            @Override 
            public Type[] getActualTypeArguments() { 
                return new Type[] {Results.class}; 
            } 

            @Override 
            public Type getRawType() { 
                return Results.class; 
            } 

            @Override 
            public Type getOwnerType() { 
                return Results.class; 
            } 
        }; 

        ClientResponse response = client.resource(controllerUri)
                                        .accept("application/json")
                                        .get(ClientResponse.class);

        if (response.getStatus() != 200)
        {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        return response.getEntity(new GenericType<ClientSidePage<T>>(){});
    }

    public T get(String domainObjectId)
    {
        ClientResponse response = client.resource(controllerUri + "/" + domainObjectId)
                                        .accept("application/json")
                                        .get(ClientResponse.class);

        if (response.getStatus() != 200)
        {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        return response.getEntity(new GenericType<T>(Product.class){});
    }
    
    public void delete(String domainObjectId)
    {
        ClientResponse response = client.resource(controllerUri + "/" + domainObjectId)
                                        .accept("application/json")
                                        .delete(ClientResponse.class);

        if (response.getStatus() != 200)
        {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
    }
}
