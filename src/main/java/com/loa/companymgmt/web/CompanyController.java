package com.loa.companymgmt.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loa.common.properties.PropertyUtil;
import com.loa.companymgmt.bl.CompanyBLImpl;
import com.loa.companymgmt.bl.ICompanyBl;
import com.loa.companymgmt.model.Company;
import com.loa.companymgmt.model.CompanyUI;
import com.loa.companymgmt.model.Owner;
import com.loa.companymgmt.model.OwnerUI;

@Controller
public class CompanyController {

	private static PropertyUtil prop = null;
	static {
		init();
	}

	public static void init() {
		try {
			setProp(new PropertyUtil("company"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setProp(PropertyUtil prop) {
		CompanyController.prop = prop;
	}

	public static PropertyUtil getProp() {
		return prop;
	}

	ICompanyBl bl = new CompanyBLImpl();

	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	int tabNo = 1;
	String success = "";
	String emailValidationStr = "";
	String companyValidationStr = "";
	
	@ModelAttribute("company")
	public Company geCompanyObject() {
		return new CompanyUI();
	}

	@ModelAttribute("owner")
	public Owner getOwnerObject() {
		return new OwnerUI();
	}
 

	@RequestMapping(value = "/companymgmt/owner.htm", method = RequestMethod.GET )
	public ModelAndView userDetails(Model model ) {
		success = "";
		tabNo = 2;
		ArrayList<Owner> ownerList = (ArrayList<Owner>) bl.getOwnerList();
		model.addAttribute("owners", ownerList);
		model.addAttribute("tabno1", tabNo);
		System.out.println("emailValidationStr :::: " + emailValidationStr + model.asMap() + "code : ");
		//model.addAttribute("emailValidationText", emailValidationText);
		return reportForm3(model, "/companymgmt/owner");
	}

	@RequestMapping(value = "/companymgmt/owner/add.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addCOwnerUI(OwnerUI owner, Model model) {
		Hashtable<String, String> map = new Hashtable<String,String>();
		try {
			Owner emailValidation = bl.getDetailsOfOwner(owner.getEmail());
			if (emailValidation != null) {
				emailValidationStr = "Email already exist";
				map.put("Error", emailValidationStr);
				
			} else {
				// valid
				if( owner.getDobStr() != null ) {
					try {
						owner.setDob(formatter.parse( owner.getDobStr()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				boolean status = bl.createOwner(owner);
				if( status ) {
					map.put("Status", "Successfully created owner " + owner.getName() +"(" + owner.getEmail() + ")");
				} else {
					map.put("Status", "Error in creating owner " + owner.getName() +"(" + owner.getEmail() + ")");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.wrap(map).toString(); 
	}
	
	@RequestMapping(value = "/companymgmt/addOwner.htm", method = RequestMethod.POST)
	public ModelAndView addOwner(@ModelAttribute OwnerUI owner, Model model) {
		tabNo = 2;
		model.addAttribute("tabno1", tabNo);
		Owner emailValidation = bl.getDetailsOfOwner(owner.getEmail());
		if (emailValidation == null) {
			// valid
			if( owner.getDobStr() != null ) {
				try {
					owner.setDob(formatter.parse( owner.getDobStr()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bl.createOwner(owner);
			emailValidationStr = "";
		} else {
			// duplicate
			emailValidationStr = "Email already exist";
		}
		model.addAttribute("emailValidationText", emailValidationStr);
		return reportForm3(model);
	}

	@RequestMapping(value = "/companymgmt/updateOwner.htm", method = RequestMethod.POST)
	public ModelAndView updateUser(@ModelAttribute OwnerUI owner, Model model) {
		tabNo = 2;
		model.addAttribute("tabno1", tabNo);
		Owner emailValidation = bl.getDetailsOfOwner(owner.getEmail());
		if (emailValidation != null && owner.getId() != emailValidation.getId() ) {
			// duplicate
			emailValidationStr = "Email already exist";
		} else {
			// valid
			if( owner.getDobStr() != null ) {
				try {
					owner.setDob(formatter.parse( owner.getDobStr()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bl.updateOwner(owner);
			emailValidationStr = "";
		}
		System.out.println("emailValidationText : " + emailValidationStr);
		model.addAttribute("emailValidationText", emailValidationStr);
		return reportForm3(model );
	}
	
	@RequestMapping(value = "/companymgmt/owner/update.htm", method = RequestMethod.POST)
	@ResponseBody
	public String updateOwner(@ModelAttribute OwnerUI owner, Model model) {
		Hashtable<String, String> map = new Hashtable<String,String>();
		
		try {
			Owner emailValidation = bl.getDetailsOfOwner(owner.getEmail());
			if (emailValidation != null && owner.getId() != emailValidation.getId() ) {
				emailValidationStr = "Email already exist";
				map.put("Error", emailValidationStr);
			} else {
				if( owner.getDobStr() != null ) {
					try {
						owner.setDob(formatter.parse( owner.getDobStr()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				boolean status = bl.updateOwner(owner);
				if( status ) {
					map.put("Status", "Successfully updated owner " + owner.getName());
				} else {
					map.put("Status", "Error in updating owner " + owner.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.wrap(map).toString();
	}

	@RequestMapping(value = "/companymgmt/company/update.htm", method = RequestMethod.POST)
	@ResponseBody
	public String updateCompanyRest(@ModelAttribute CompanyUI company, Model model) {
		Hashtable<String, String> map = new Hashtable<String,String>();
		
		try {
			Company c = bl.checkDetailsOfCompany(company.getName());

			if (c == null ) {
				companyValidationStr = "Company " + company.getName() + " not found";
				map.put("Error", companyValidationStr);
			} else {
				boolean status = bl.updateCompanyByName(company);
				if (company.getOwnerIds() != null && !company.getOwnerIds().equals("")) {
					String[] ids = company.getOwnerIds().split(",");
					for (String oid : ids) {
						 bl.addBeneficialOwner(company.getName(), Integer.parseInt(oid));
					}
				}
				if( status ) {
					map.put("Status", "Successfully updated company " + company.getName());
				} else {
					map.put("Status", "Error in updating company " + company.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.wrap(map).toString();
	}

	@RequestMapping(value = "/companymgmt/updateCompany.htm", method = RequestMethod.POST)
	public ModelAndView updateCompany(@ModelAttribute CompanyUI company,
			Model model) {
		try {
			Company c = bl.getDetailsOfCompany(company.getName());

			if (c != null && company.getId() != c.getId()) {
				companyValidationStr = "Company name already exist";
				//tabNo = 1;
				//model.addAttribute("tabno1", tabNo);
				//return reportForm3(model);
			//} else if (company.getOwnerIds() != null && !company.getOwnerIds().equals("")) { 
				//companyValidationStr = "Please select at least one Beneficial Owner";
				//tabNo = 1;
				//model.addAttribute("tabno1", tabNo);
				//return reportForm3(model);
			} else {
				companyValidationStr = "";
				bl.updateCompany(company);
				
				String[] ids = company.getOwnerIds().split(",");
				for (String oid : ids) {
					 bl.addBeneficialOwner(company.getName(), Integer.parseInt(oid));
				}
				
				//String[] ids = company.getOwnerIds().split(",");
				//for (String oid : ids) {
				//	 bl.addBeneficialOwner(company.getName(), Integer.parseInt(oid));
				//}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tabNo = 1;
		model.addAttribute("tabno1", tabNo);
		model.addAttribute("companyValidationStr", companyValidationStr);
		return reportForm3(model);
	}

	@RequestMapping(value = "/companymgmt/company/add.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addCompany(@ModelAttribute CompanyUI company,
			Model model) {
		Hashtable<String, String> map = new Hashtable<String,String>();
		try {
			Company c = bl.checkDetailsOfCompany(company.getName());
			if (c != null) {
				companyValidationStr = "Company name already exist";
				map.put("Error", companyValidationStr);
			} else {
				boolean status = bl.createCompany(company);
				if (company.getOwnerIds() != null && !company.getOwnerIds().equals("")) {
					String[] ids = company.getOwnerIds().split(",");
					for (String oid : ids) {
						 bl.addBeneficialOwner(company.getName(), Integer.parseInt(oid));
					}
				}
				if( status ) {
					map.put("Status", "Successfully created company " + company.getName());
				} else {
					map.put("Status", "Error in creating company " + company.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.wrap(map).toString(); 
	}

	
	@RequestMapping(value = "/companymgmt/addCompany.htm", method = RequestMethod.POST)
	public ModelAndView addBusiness(@ModelAttribute CompanyUI company,
			Model model) {
		try {
			Company c = bl.checkDetailsOfCompany(company.getName());

			if (c != null) {
				companyValidationStr = "Company name already exist";
				tabNo = 1;
				model.addAttribute("tabno1", tabNo);
				//return reportForm3(model);
			} else {
				companyValidationStr = "";
				bl.createCompany(company);
				if (company.getOwnerIds() != null && !company.getOwnerIds().equals("")) {
					String[] ids = company.getOwnerIds().split(",");
					for (String oid : ids) {
						 bl.addBeneficialOwner(company.getName(), Integer.parseInt(oid));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tabNo = 1;
		model.addAttribute("tabno1", tabNo);
		model.addAttribute("companyValidationStr", companyValidationStr);
		return reportForm3(model);
	}
	
	@RequestMapping(value = "/companymgmt/company/get.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getDetailsOfCompanyRest(@ModelAttribute CompanyUI company,
			Model model) {
		try {
			Company c =  bl.getCompanyDetails(company.getName() );
			return JSONObject.wrap(c).toString(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error in getting company details for : " + company.getName();
	}
	
	@RequestMapping(value = "/companymgmt/owner/get.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getDetailsOfOwner(@ModelAttribute OwnerUI owner,
			Model model) {
		try {
			Owner o =  bl.getDetailsOfOwner(owner );
			return JSONObject.wrap(o).toString(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error in getting owner details for : " + owner.getId();
	}
	
	@RequestMapping(value = "/companymgmt/company/list.htm", method = RequestMethod.GET )
	@ResponseBody
	public String listCompanies(Model model ) {
		ArrayList<String> companyList = (ArrayList<String>) bl.getCompanyList();
		return JSONObject.wrap(companyList).toString();
	}
	
	@RequestMapping(value = "/companymgmt/owner/list.htm", method = RequestMethod.GET )
	@ResponseBody
	public String listOwners(Model model ) {
		ArrayList<Owner> ownerList = (ArrayList<Owner>) bl.getOwnerList();
		return JSONObject.wrap(ownerList).toString();
	}
	
	/*@RequestMapping(value = "/companymgmt/getCompany.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getDetailsOfCompany(@ModelAttribute CompanyUI company,
			Model model) {
		try {
			System.out.println(model.toString());
			Company c =  bl.getDetailsOfCompany(company.getName() );
			System.out.println("name L: "  + c.getName());
			model.addAttribute("company2", c);
			model.addAttribute("company2name", c.getName());
			System.out.println(JSONObject.wrap(c).toString());
			return JSONObject.wrap(c).toString(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		tabNo = 1;
		model.addAttribute("tabno1", tabNo);
		//return reportForm3(model, "/companymgmt/company");
		return null;
	}*/
	
	@RequestMapping(value = "/companymgmt/company/addowners.htm", method = RequestMethod.POST )
	@ResponseBody
	public String addOwnersToCompanies(String companyName, String owners,Model model ) {
		Hashtable<String, String> map = new Hashtable<String,String>();
		
		Company c = bl.checkDetailsOfCompany(companyName);
		if( c == null ) {
			map.put("Error", "Company " + companyName + " not found");
			return map.toString();
			}
		String[] names = owners.split(",");
		for (String owner : names) {
			Owner o = new Owner();
			o.setName(owner);
			Owner emailValidation = bl.getDetailsOfOwner(o.getName(), o.getEmail());
			if(emailValidation == null ) {
				bl.createOwner(o);
			} else {
				o = emailValidation;
			}
			bl.addBeneficialOwner(companyName, o.getId());
		}
		map.put("Status", "Added owners successfully" );
		return map.toString();
	}
	
	@RequestMapping(value = "/companymgmt/company.htm", method = RequestMethod.GET )
	public ModelAndView userDetails1(Model model ) {
		success = "";
		tabNo = 1;
		ArrayList<String> companyList = (ArrayList<String>) bl.getCompanyList();
		ArrayList<Owner> ownerList = (ArrayList<Owner>) bl.getOwnerList();
		model.addAttribute("company1", companyList);
		model.addAttribute("owners1", ownerList);
		model.addAttribute("tabno1", tabNo);
		System.out.println("emailValidationStr :::: " + emailValidationStr + model.asMap() + "code : ");
		//model.addAttribute("emailValidationText", emailValidationText);
		return reportForm3(model, "/companymgmt/company");
	}


	@RequestMapping(value = "/companymgmt/company1.htm", method = RequestMethod.GET)
	public ModelAndView businessDetails(Model model) {
		success = "";
		companyValidationStr = "";
		emailValidationStr = "";
		tabNo = 1;
		ArrayList<String> companyList = (ArrayList<String>) bl.getCompanyList();
		model.addAttribute("company", companyList);
		model.addAttribute("companyNameValidationText", companyValidationStr);
		ArrayList<Owner> ownerList = (ArrayList<Owner>) bl.getOwnerList();
		model.addAttribute("owners", ownerList);
		model.addAttribute("tabno1", tabNo);
		return reportForm3(model, "/companymgmt/company");
	}

	public ModelAndView reportForm3(Model model) {
		//return "redirect:account/accountReport.htm"; // book/create.jsp
		ModelAndView modelAndView =  new ModelAndView("redirect:/companymgmt/companyMain.htm");
		modelAndView.addAllObjects(model.asMap());
		return modelAndView;
	}
	
	public ModelAndView reportForm3(Model model, String url) {
		//return "redirect:account/accountReport.htm"; // book/create.jsp
		ModelAndView modelAndView =  new ModelAndView(url);
		modelAndView.addAllObjects(model.asMap());
		return modelAndView;
	}
	
	@RequestMapping(value = "/companymgmt/companyMain.htm", method = RequestMethod.GET)
	public String reportForm1(Model model) {
		return "companymgmt/companyMain"; // book/create.jsp
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		binder.registerCustomEditor(Set.class, "owners",
				new CustomCollectionEditor(Set.class) {
					protected Object convertElement(Object element) {
						try {
							if (element instanceof Owner) {
								return element;
							}
							if (element instanceof String) {
								Owner staff = new Owner();
								staff.setId((Integer) element);
								return staff;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}
				});
	}

 
	
	public static String getDateFormat(Date date){
		DateFormat df = new SimpleDateFormat("EEE MMM d yyyy");
		String reportDate = df.format(date);
		return reportDate;
	  }
	
	

}