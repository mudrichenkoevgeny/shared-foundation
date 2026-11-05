document.addEventListener('DOMContentLoaded', function() {
    var script = document.createElement('script');
    script.src = 'https://unpkg.com/kotlin-playground@1/dist/playground.min.js';
    script.onload = function() {
        KotlinPlayground('.kotlin');
    };
    document.head.appendChild(script);

    // Algolia DocSearch Injection
    var algoliaCss = document.createElement('link');
    algoliaCss.rel = 'stylesheet';
    algoliaCss.href = 'https://cdn.jsdelivr.net/npm/@docsearch/css@3';
    document.head.appendChild(algoliaCss);

    var algoliaJs = document.createElement('script');
    algoliaJs.src = 'https://cdn.jsdelivr.net/npm/@docsearch/js@3';
    algoliaJs.onload = function() {
        var searchContainer = document.createElement('div');
        searchContainer.id = 'docsearch';
        // Append searchContainer to header
        var header = document.querySelector('.navigation-controls');
        if (header) {
            header.insertBefore(searchContainer, header.firstChild);
        }

        docsearch({
            appId: 'APP_ID_PLACEHOLDER',
            apiKey: 'API_KEY_PLACEHOLDER',
            indexName: 'shared-foundation',
            container: '#docsearch',
            debug: false
        });
    };
    document.head.appendChild(algoliaJs);
});
