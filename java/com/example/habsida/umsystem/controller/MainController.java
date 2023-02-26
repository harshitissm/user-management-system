package com.example.habsida.umsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.habsida.umsystem.model.Role;
import com.example.habsida.umsystem.model.User;
import com.example.habsida.umsystem.repository.UserRepository;
import com.example.habsida.umsystem.service.UserService;




@Controller
//@RequestMapping("/user")
public class MainController {

	private UserService userService; 
	
	private UserRepository userRepository;
	
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    
//    @Autowired
//	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	public MainController(UserService userService) {
		super();
		this.userService = userService;
	}
//	
//	@Autowired
//    public MainController(UserService userService,
//                          CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
//                          AuthenticationManager authenticationManager) {
//        this.userService = userService;
//        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
//        this.authenticationManager = authenticationManager;
//    }
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
//	@PostMapping("/login")
//	public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    try {
//	    	
//	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
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

	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
		
		User user = userRepository.findByEmail(email);
		Role role = user.getRoles().get(0);
		if(role.getName().contentEquals("ADMIN"))
			return "redirect:/";
		return "redirect:/userSuccess";
		
	}
	
	@GetMapping("/user")
	public String userPage(Model theModel) {
				
		theModel.addAttribute("users", userService.getAllUsers());

		return "user";
	}
	
	@GetMapping("/")
	public String listUsers(Model theModel) {
		
		// get customers from the servic and add the customer to the model
		theModel.addAttribute("users", userService.getAllUsers());
		
		return "index";
	}
	
	@GetMapping("/edit/{id}")
	public String editUserForm(@PathVariable Long id, Model model) {
		
		model.addAttribute("user", userService.getUserById(id));
		return "edit_user";
	}
	
	@PostMapping("/{id}")
	public String updateUser(@PathVariable Long id,
			@ModelAttribute("user") User user,
			Model model) {
		
		userService.updateUser(user);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteStudentById(id);
		return "redirect:/";
	}
}





















