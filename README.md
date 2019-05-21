# Non-exaustive TODO lists
## Code related
- Model
    - Add more constrains like "nullable = false"
    - Choose a proper fetch type
    - Overriden toString must take into account null values?
- DTO
    - Could we use entities instead of DTO?
        - If we use DTOs, we must change test classes accordingly
- Validation
    - Use Validations and custom validation annotation
- Error handling
    - Implement an ErrorHandler class
        - Implement custom Exceptions
- Optimization
    - Limit the amount of result sent to the front-end
- Best practises
    - POSTs should return the created entity
    - UPDATEs should return the updated entity
    - The update end-point should avoid mapping if possible
    - All fields must be private, if that makes sense for them
- Test coverage
    - Unit test mappers and converts
## Features
- Each design can have 0 or more tags
- Each design has a description
- Design results can be filtered
- Add DELETE end-points
    - Choose a proper cascade type