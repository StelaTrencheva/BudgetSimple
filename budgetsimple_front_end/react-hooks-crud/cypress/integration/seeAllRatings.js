
describe('Rate the Application', () => {
    it('Rates the experience in the application', () => {
        const username = "admin";
        const password = "admin123"
        cy.request('POST', 'http://localhost:8080/user/login', {
            username,
            password
        })

        cy.visit('http://localhost:3000/allRatings');
        cy.get(':nth-child(1) > .card-body > .row > :nth-child(1) > a > .smallLogo').click();
        cy.get(':nth-child(1) > .col-lg-12 > :nth-child(1)').should('have.text', 'Question: What do you like most about our website?');
        cy.get(':nth-child(2) > .col-lg-12 > :nth-child(1)').should('have.text', 'Question: How often do you visit and use BudgetSimple?');
        cy.get(':nth-child(3) > .col-lg-12 > :nth-child(1)').should('have.text', 'Question: Do you have any advice for us? Write it here!');
        cy.get(':nth-child(4) > .col-lg-12 > :nth-child(1)').should('have.text', 'Question: On a scale of 1-10, how likely are you to recommend our website to your friends or family?');
        cy.get(':nth-child(5) > .col-lg-12 > :nth-child(1)').should('have.text', 'Question: Did you face any challenge while using our website?');
        
    })

})