
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
        cy.contains('Question: What do you like most about our website?').should('be.visible')
        cy.contains('How often do you visit and use BudgetSimple?').should('be.visible')
        cy.contains('Question: Do you have any advice for us? Write it here!').should('be.visible')
        cy.contains('Question: On a scale of 1-10, how likely are you to recommend our website to your friends or family?').should('be.visible')
        cy.contains('Question: Did you face any challenge while using our website?').should('be.visible')
        
    })

})