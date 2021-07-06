package bg.infosys.interns.bmanagement.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bg.infosys.interns.bmanagement.core.dto.ProductRecipeFilterDTO;
import bg.infosys.interns.bmanagement.core.page.PagingSorting;
import bg.infosys.interns.bmanagement.ws.config.ControllerConfig;
import bg.infosys.interns.bmanagement.ws.dto.ProductRecipeDTO;
import bg.infosys.interns.bmanagement.ws.service.ProductRecipeService;
import bg.infosys.interns.bmanagement.ws.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Orders")
@RequestMapping(ControllerConfig.PRODUCTRECIPE_URL)
public class ProductRecipeController {
	
	private ProductRecipeService productRecipeService;

	public ProductRecipeController(ProductRecipeService productRecipeService) {
		this.productRecipeService = productRecipeService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all ProductRecipe", notes = "This method will return all ProductRecipe")
	public List<ProductRecipeDTO>getAll(){
		return productRecipeService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one ProductRecipe", notes = "This method will get the ProductRecipe with given id")
	public ProductRecipeDTO getProductRecipe(@PathVariable(value = "id") Integer id){
		return productRecipeService.findById(id);
	}
	
	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of ProductRecipe", notes = "This method will return page of ProductRecipe")
	public Page<ProductRecipeDTO> getProductRecipePaging(
			@RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "5") int size,
	        @RequestParam(defaultValue = "id", required = false) String sortBy,
	        @RequestParam(defaultValue = "asc", required = false) String sortDirection,
	        @RequestParam(required = false) String recipe,
	        @RequestParam(required = false) String shop,
	        @RequestParam(required = false)Integer productQuantity,
	        @RequestParam(required = false) String product){
		
		return productRecipeService.findAllByFilter(new ProductRecipeFilterDTO(recipe,shop,productQuantity,product),
											 new PagingSorting(page-1, size, sortBy, sortDirection));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new ProductRecipe", notes = "This method will create new ProductRecipe")
	public ResponseEntity<ProductRecipeDTO> create(@RequestBody @Valid ProductRecipeDTO productRecipeDTO) {
		
		return ResponseEntity.status(201).body(productRecipeService.save(productRecipeDTO));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update ProductRecipe", notes = "This method will update the ProductRecipe with given id or will return HTTP Status 404 Not Found")
	public ResponseEntity<ProductRecipeDTO> update(@PathVariable Integer id, @RequestBody @Valid ProductRecipeDTO productRecipe) {
		ProductRecipeDTO updateProductRecipe = productRecipeService.update(id, productRecipe);
		
		return ResponseEntity.status(200).body(updateProductRecipe);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete ProductRecipe", notes = "This method will delete the ProductRecipe matching the passed id")
	public void delete(@PathVariable Integer id) {
		productRecipeService.deleteById(id);
	}
}
