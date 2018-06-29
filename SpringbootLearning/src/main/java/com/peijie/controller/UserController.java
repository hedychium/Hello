package com.peijie.controller;

import com.peijie.entity.User;
import com.peijie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @RequestMapping("/list")
    public List<User> getList() {
        return userRepository.findAll();
    }

    @RequestMapping("/save")
    public User save(User user) {
        return userRepository.save(user);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public List<User> delete(Long id) {
        userRepository.delete(id);
        return userRepository.findAll();
    }

    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        String result = "数博欢迎你,";
        boolean flag = true;
        User user1 = (User) userRepository.findOne(new Specification<User>() {
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                /*criteriaQuery.where(criteriaBuilder.equal(root.<String>get("name"), user.getName()),
                        criteriaBuilder.equal(root.<String>get("password"), user.getPwd()));*/
                criteriaQuery.where(criteriaBuilder.equal(root.<String>get("name"), user.getName()));
                return null;
            }
        });
        if (user1 == null) {
            flag = false;
            result = "用户不存在，登陆失败";
            request.getSession().setAttribute("register_user",result);
        } else if (!user1.getPwd().equals(user.getPwd())) {
            flag = false;
            result = "用户名密码不相符，登陆失败";
            request.getSession().setAttribute("register_user",result);
        }
        if(flag){
            request.getSession().setAttribute("register_user",result+user1.getName());
        }
        return "index";
    }
}
