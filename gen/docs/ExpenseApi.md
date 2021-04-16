# ExpenseApi

All URIs are relative to *https://virtserver.swaggerhub.com/jyadegari/expenses/1.0.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**expenseExpenseIdGet**](ExpenseApi.md#expenseExpenseIdGet) | **GET** /expense/{expenseId} | 
[**expenseExpenseIdPut**](ExpenseApi.md#expenseExpenseIdPut) | **PUT** /expense/{expenseId} | 
[**expenseGet**](ExpenseApi.md#expenseGet) | **GET** /expense | 
[**expensePost**](ExpenseApi.md#expensePost) | **POST** /expense | 


<a name="expenseExpenseIdGet"></a>
# **expenseExpenseIdGet**
> Expense expenseExpenseIdGet(expenseId)



Get a specified expense by id

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExpenseApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://virtserver.swaggerhub.com/jyadegari/expenses/1.0.0");

    ExpenseApi apiInstance = new ExpenseApi(defaultClient);
    Integer expenseId = 78; // Integer | 
    try {
      Expense result = apiInstance.expenseExpenseIdGet(expenseId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExpenseApi#expenseExpenseIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expenseId** | **Integer**|  |

### Return type

[**Expense**](Expense.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | shows selected expense based on expense id. |  -  |
**400** | bad input parameter |  -  |

<a name="expenseExpenseIdPut"></a>
# **expenseExpenseIdPut**
> Expense expenseExpenseIdPut(expenseId, expense)



Update an existing expense

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExpenseApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://virtserver.swaggerhub.com/jyadegari/expenses/1.0.0");

    ExpenseApi apiInstance = new ExpenseApi(defaultClient);
    Integer expenseId = 78; // Integer | 
    Expense expense = new Expense(); // Expense | Update an expense
    try {
      Expense result = apiInstance.expenseExpenseIdPut(expenseId, expense);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExpenseApi#expenseExpenseIdPut");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expenseId** | **Integer**|  |
 **expense** | [**Expense**](Expense.md)| Update an expense | [optional]

### Return type

[**Expense**](Expense.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Shows updated expense |  -  |
**400** | bad input parameter |  -  |

<a name="expenseGet"></a>
# **expenseGet**
> List&lt;Expense&gt; expenseGet()



Get all the expenses

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExpenseApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://virtserver.swaggerhub.com/jyadegari/expenses/1.0.0");

    ExpenseApi apiInstance = new ExpenseApi(defaultClient);
    try {
      List<Expense> result = apiInstance.expenseGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExpenseApi#expenseGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Expense&gt;**](Expense.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Shows list of all expenses. |  -  |

<a name="expensePost"></a>
# **expensePost**
> expensePost(expense)



Adds an expense to the list of expenses

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExpenseApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://virtserver.swaggerhub.com/jyadegari/expenses/1.0.0");

    ExpenseApi apiInstance = new ExpenseApi(defaultClient);
    Expense expense = new Expense(); // Expense | Add an expense
    try {
      apiInstance.expensePost(expense);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExpenseApi#expensePost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expense** | [**Expense**](Expense.md)| Add an expense | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | item created |  -  |
**400** | invalid input, object invalid |  -  |
**409** | an existing item already exists |  -  |

