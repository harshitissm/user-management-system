package com.example.habsida.umsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.habsida.umsystem.dto.UserRegistrationDto;
import com.example.habsida.umsystem.model.User;
import com.example.habsida.umsystem.service.UserService;

@Controller
//@RequestMapping("/user")
public class MainController {

	private UserService userService; 
	
	
//   @Autowired
//    private AuthenticationManager authenticationManager;
    
//    @Autowired
//	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	public MainController(UserService userService) {
		super();
		this.userService = userService;
	}
	
//	@Autowired
//    public MainController(UserService userService,
//                          CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
//                          AuthenticationManager authenticationManager) {
//        this.userService = userService;
//        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
////        this.authenticationManager = authenticationManager;
//    }
	
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
	
//	@PostMapping("/login")
//	public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    try {
//	    	
////	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
////	        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//	        HttpSession session = request.getSession(true);
//	        session.setMaxInactiveInterval(30 * 60);
//
//	        UserDetails userDetails = userService.loadUserByUsername(email);
//
//	        if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
//	            customAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
//	            return "redirect:/";
//	        } else {
//	            customAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
//	            return "redirect:/userSuccess";
//	        }
//
//	    } catch (AuthenticationException e) {
//	        return "redirect:/login?error";
//	    }
//	}

	
	@GetMapping("/user")
	public String userPage(Model theModel) {
				
		theModel.addAttribute("users", userService.getOnlyUsers());

		return "user";
	}
	
	@GetMapping("/admin")
	public String listUsers(Model theModel) {
		
		
		theModel.addAttribute("users", userService.getAllUsers());
		
		theModel.addAttribute("userss", userService.getOnlyUsers());
		
		theModel.addAttribute("userDto", new UserRegistrationDto());

		return "index2";
	}
	
	@GetMapping("/access-denied")
	public String errorPage() {
				
		return "redirect:/login?error";
	}
	
	@GetMapping("/edit/{id}")
	public String editUserForm(@PathVariable Long id, Model theModel) {
		
		theModel.addAttribute("user", userService.getUserById(id));
		
		theModel.addAttribute("users", userService.getAllUsers());
		
		theModel.addAttribute("userss", userService.getOnlyUsers());
		
		theModel.addAttribute("userDto", new UserRegistrationDto());
		return "index";
	}
	
	@PostMapping("/{id}")
	public String updateUser(@PathVariable Long id, @RequestParam("role") String role,
			@ModelAttribute("user") User user,
			Model model) {
		
		userService.updateUser(user, role);
		return "redirect:/admin";
	}
	
	@GetMapping("/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteStudentById(id);
		return "redirect:/admin";
	}
	
	@PostMapping("/registration")
	public String registrationUserAccount(@ModelAttribute("user") UserRegistrationDto registratoinDto) {
		userService.save(registratoinDto);
		return "redirect:/admin";
	}
}





















