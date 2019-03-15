# Code Bits

This is JavaScript and grabs something in the window and resuses it.
```
browser
    //Open browser and wait for alert to show
      .url(url)
      .useXpath().waitForElementVisible(button, oneMinuteTimeout)
      .assert.title(title)
      .useXpath().click(button)
    //Open search and click on first featured story
      .useCss().click(searchBar)
      .useXpath().waitForElementVisible(element, oneMinuteTimeout)
      .getText(firstResult, function(result) {
        firstResultText = result.value
      })
      .waitForElementVisible(firstResult, oneMinuteTimeout)
      .click(firstResult)
    //Wait for map to redirect/pan to search result, then assert that the story displayed is the story that was searched for
      .useCss().waitForElementVisible(poiCssSelector, oneMinuteTimeout)
      .perform(function() {
        browser
          .elements('css selector', poiCssSelector, function(elements){
            for(let i = 0; i < elements.value.length; i++) {
              browser.elementIdText(elements.value[i].ELEMENT, function(text){
                var storyText = text.value
                if(storyText.includes(firstResultText)) {
                  //Click on story to play snap
                  browser.elementIdClick(elements.value[i].ELEMENT)
                  return;
                }
              })
            }
          })
          .perform(function() {
            browser.useCss()
            //Verify that snap popover presents
              .waitForElementPresent(element, oneMinuteTimeout)
              .end();
          })
      })
    }
```
