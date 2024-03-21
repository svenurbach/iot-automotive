describe('Test FindMyCar', () => {
  it('Component', () => {
    cy.visit('http://localhost:4200/')
    cy.get('.entity-grid li').should('have.length.greaterThan', 0).as('cars')
    cy.get('@cars').first().find('.icon svg').should('be.visible')
    cy.get('@cars').first().click()
    cy.location('href').should('include', '/vehicle/')
    cy.get('h1').should('contain', 'Fahrzeug')
    cy.get('.openstreetmap').should('be.visible')
  })
})
