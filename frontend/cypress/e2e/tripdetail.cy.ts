describe('template spec', () => {
  it('Navigate to Trip Detail from Trip Overview', () => {
    cy.visit('http://localhost:4200/trips')
    cy.get('ul.entity-grid').find('li').first().click();
    cy.get('h2').should('be.visible').and("contain", "Start");
    cy.get('google-map').should('be.visible')
    cy.get('div[aria-label="Start"]').should('be.visible')
    cy.get('div.start-end-point div.address p').should('not.be.empty')
    cy.get('img[src$=".svg"]').should("have.length", 4)
    cy.get('span.trip-status').should(($spans) => {
      let containsMatch = false;
      $spans.each((index, span) => {
        const text = Cypress.$(span).text().trim();
        if (/RUNNING|PAUSED|FINISHED/.test(text)) {
          containsMatch = true;
          return false;
        }
      });
      expect(containsMatch).to.be.true;
    });

  });
})
