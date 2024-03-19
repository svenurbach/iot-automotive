describe('template spec', () => {
  it('Test Trip Overview Page', () => {
    cy.visit('http://localhost:4200/trips')
    cy.get('h1').should('contain', 'Fahrten')
    cy.get('mat-select#vehicle-select').should('be.visible')
    cy.get('mat-date-range-input').should('be.visible')
    cy.get('svg.mat-datepicker-toggle-default-icon').should('have.attr', 'width', '24px')
    cy.get('div.trip-stats').children().should('have.length', 4).find('img[src$=".svg"]')
      .should('exist');
    cy.get('ul.entity-grid').children().then(($children) => {
      if ($children.length > 0) {
        cy.get('ul.entity-grid li').find('google-map').should('exist')
        cy.get('ul.entity-grid li').find('span.date-type').should('exist')
        cy.get('ul.entity-grid li').find('span.trip-status').should(($spans) => {
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
        cy.get('h2').invoke('text').then((text) => {
          // Extract the number from the text using a regular expression
          const numberInH2 = parseInt(text.match(/\d+/)[0], 10);
          cy.get('ul.entity-grid li').children().should('have.length', numberInH2);
        });
        cy.get('mat-datepicker-toggle').click();
        cy.get('mat-calendar').should('be.visible');
      }
    })
  })
})
