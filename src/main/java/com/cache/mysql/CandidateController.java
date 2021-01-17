package com.cache.mysql;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

@RestController
public class CandidateController {

	@Autowired
	private CandidateRepository repo;
	
	@Autowired
	private Jedis jedis;
	

	@GetMapping("/candidates")
	public Iterable<Candidate> findAll() {
		return repo.findAll();
	}
	
	@GetMapping("/candidate")
	public Candidate findById(@RequestParam Integer id) {
		// Step 1: check if the data exist
		Map<String, String> fields = jedis.hgetAll(id.toString());
		Candidate response = null;
		if(fields.isEmpty()) {
			// NOT FOUND IN CACHE
			System.out.println("FROM DB");
			// STEP 2: GET from database
			response = repo.findById(id).get();
			
			// STEP 3: SET IN CACHE FOR NEXT TIME
			jedis.hset(id.toString(), "NAME", response.getName());
			jedis.hset(id.toString(), "SKILLS", response.getSkills());
						
		}else {
			// FOUND IN CACHE
			System.out.println("FROM CACHE");
			response = new Candidate();
			response.setName(fields.get("NAME"));
			response.setSkills(fields.get("SKILLS"));
		}
		return response;
	}
	
	@PostMapping("/candidate")
	public String addNew() {
		Candidate candidate = new Candidate();
		candidate.setName("Name_" + UUID.randomUUID().toString());
		candidate.setSkills("Rest API");
		repo.save(candidate);
		return "DONE";
	}
}
