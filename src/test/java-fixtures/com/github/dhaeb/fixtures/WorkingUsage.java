/* 
 * Copyright 2014 Dan Häberlein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.github.dhaeb.fixtures;

import java.util.regex.Pattern;

import com.github.dhaeb.validate.ValidateRegex;

public class WorkingUsage {

	@ValidateRegex
	private static final String CONSTANT = "[^123]*"; // works as expected
	
	@ValidateRegex
	private final String field = "pattern"; // works only with a final field!
	
	public boolean matches(String input){
		@ValidateRegex final String localVariable = "compilable"; // this is not processed using javac 1.7.55!!!
		Pattern compile = Pattern.compile(localVariable);
		return compile.matcher(input).matches();
	}
	
}
