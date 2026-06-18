# MoveBuddy - Casual Social Wellness Platform

MoveBuddy is a mobile-first Progressive Web App (PWA) designed to combat youth social isolation and physical inactivity by transforming daily movement into a gamified, community-driven social experience. The platform leverages lightweight web technologies to connect young people for low-pressure, localized outdoor activities like walking and cycling, eliminating the friction of native app stores and performance-heavy fitness tracking.

## Software Architecture Overview

The application utilizes a decoupled monorepo architecture engineered for high scalability, rapid deployment, and data privacy compliance.

The frontend client is built as a Progressive Web App using responsive components optimized for mobile viewports, ensuring cross-platform capability with a single codebase. It interfaces with device-level geolocation utilities to facilitate localized matchmaking without persistently tracking user footprints.

The backend service is powered by a robust framework handling secure user authentication, proximity-matching algorithms, and gamification logic. It provides a RESTful API layer that manages secure connections and transaction logic for reward redemptions.

The persistence layer relies on a relational database system optimized with spatial extensions to handle location-based queries efficiently, ensuring rapid indexing of nearby active community groups.

## Core System Features

The platform operates through three unified service engines designed to maximize user retention and prevent ecosystem bypass.

The Proximity Discovery Engine computes anonymous neighborhood clusters, allowing users to discover and join active walking or cycling groups within a localized radius based on real-time availability.

The Gamification & Achievement Ledger tracks individual contributions to collective milestones, distributing social wellness points upon verified activity completion.

The Reward Redemption Module manages digital vouchers and integration webhooks with local brand partners, enabling users to exchange accumulated points for tangible local incentives.