package edu.citybike.controller;

import java.util.ArrayList;
import java.util.List;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.citybike.model.Bike;

@Controller
@SessionAttributes
public class AddNewBikeController {
	private static final Logger logger = LoggerFactory.getLogger(AddNewBikeController.class);
	
	@RequestMapping(value = "/addNewBike", method = RequestMethod.POST)
    public String addNewBike(@ModelAttribute("newBike")
                            Bike bike) {
        logger.info("Nazwa: "+bike.getTechnicalDetails().getName()+" \nNumer wypozyczalni: "+bike.getRentalOfficeCode()); 
         //System.out.println("Nazwa: "+bike.getTechnicalDetails().getName()+" \nNumer wypozyczalni: "+bike.getRentalOfficeCode());
        return "redirect:/addNewBike";
    }
	
	@RequestMapping("/addNewBike")
    public ModelAndView showBikes() {
         List<String> rentalOfficeCodeList = new ArrayList<String>();
         rentalOfficeCodeList.add("Code 1");
         rentalOfficeCodeList.add("Code 2");
         rentalOfficeCodeList.add("Code 3");
         ModelAndView modelAndView = new ModelAndView("addnewbike");
         modelAndView.addObject("rentalOfficeCodeList", rentalOfficeCodeList);
         modelAndView.addObject("newBike", new Bike());
        return modelAndView;
    }

}
