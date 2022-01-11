
describe('Rate the Application', () => {
    it('Rates the experience in the application', () => {
        const username = "admin";
        const password = "admin123"
        cy.request('POST', 'http://localhost:8080/user/login', {
            username,
            password
        })

        cy.visit('http://localhost:3000/allRatings');
        cy.get('.btn').click();
        cy.get('#title').clear();
        cy.get('#title').type('New survey');
        cy.get('#question1').click();
        cy.get('#question1').type('Question 1');
        cy.get('#question2').click();
        cy.get('#question2').type('Question 2');
        cy.get('#question3').click();
        cy.get('#question3').type('Question 3');
        cy.get('#question4').click();
        cy.get('#question4').type('Question 4');
        cy.get('#question5').click();
        cy.get('#question5').type('Question 5');
        cy.get('.btn').click();
        cy.url().should('be.equal', 'http://localhost:3000/allRatings')
    })

})