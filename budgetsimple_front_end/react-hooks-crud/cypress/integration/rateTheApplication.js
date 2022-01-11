
describe('Rate the Application', () => {
    it('Rates the experience in the application', () => {
        const username = "testUser";
        const password = "testUser123"
        cy.request('POST', 'http://localhost:8080/user/login', {
            username,
            password
        })

        cy.visit('http://localhost:3000/user/rateUs');
        cy.get('.empty-icons > :nth-child(5)').click();
        cy.get('#answer-What\\ do\\ you\\ like\\ most\\ about\\ our\\ website\\?').click();
        cy.get('#answer-What\\ do\\ you\\ like\\ most\\ about\\ our\\ website\\?').type('The design');
        cy.get('#answer-How\\ often\\ do\\ you\\ visit\\ and\\ use\\ BudgetSimple\\?').click();
        cy.get('#answer-How\\ often\\ do\\ you\\ visit\\ and\\ use\\ BudgetSimple\\?').type('Every day!');
        cy.get('#answer-Do\\ you\\ have\\ any\\ advice\\ for\\ us\\?\\ Write\\ it\\ here\\!').click();
        cy.get('#answer-Do\\ you\\ have\\ any\\ advice\\ for\\ us\\?\\ Write\\ it\\ here\\!').type('Everything is good');
        cy.get('#answer-On\\ a\\ scale\\ of\\ 1-10\\,\\ how\\ likely\\ are\\ you\\ to\\ recommend\\ our\\ website\\ to\\ your\\ friends\\ or\\ family\\?').click();
        cy.get('#answer-On\\ a\\ scale\\ of\\ 1-10\\,\\ how\\ likely\\ are\\ you\\ to\\ recommend\\ our\\ website\\ to\\ your\\ friends\\ or\\ family\\?').type('10');
        cy.get('#answer-Did\\ you\\ face\\ any\\ challenge\\ while\\ using\\ our\\ website\\?').click();
        cy.get('#answer-Did\\ you\\ face\\ any\\ challenge\\ while\\ using\\ our\\ website\\?').type('No!')
        cy.get('.btn').click();
        cy.url().should('be.equal', 'http://localhost:3000/user/account')
    })

})