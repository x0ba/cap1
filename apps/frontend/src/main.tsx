import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { TooltipProvider } from "@/components/ui/tooltip";
import "./index.css";
import App from "./App.tsx";
import { AccountsPage } from "./pages/AccountsPage.tsx";
import { CardsPage } from "./pages/CardsPage.tsx";
import { CreditWisePage } from "./pages/CreditWisePage.tsx";
import { DepositsPage } from "./pages/DepositsPage.tsx";
import { HelpSupportPage } from "./pages/HelpSupportPage.tsx";
import { MoveMoneyPage } from "./pages/MoveMoneyPage.tsx";
import { OverviewPage } from "./pages/OverviewPage.tsx";
import { PayBillsPage } from "./pages/PayBillsPage.tsx";
import { RewardsPage } from "./pages/RewardsPage.tsx";
import { SavingsGoalsPage } from "./pages/SavingsGoalsPage.tsx";
import { SettingsPage } from "./pages/SettingsPage.tsx";
import { SpendingInsightsPage } from "./pages/SpendingInsightsPage.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      { index: true, element: <OverviewPage /> },
      { path: "accounts", element: <AccountsPage /> },
      { path: "cards", element: <CardsPage /> },
      { path: "move-money", element: <MoveMoneyPage /> },
      { path: "pay-bills", element: <PayBillsPage /> },
      { path: "deposits", element: <DepositsPage /> },
      { path: "savings-goals", element: <SavingsGoalsPage /> },
      { path: "spending-insights", element: <SpendingInsightsPage /> },
      { path: "creditwise", element: <CreditWisePage /> },
      { path: "rewards", element: <RewardsPage /> },
      { path: "settings", element: <SettingsPage /> },
      { path: "help-support", element: <HelpSupportPage /> },
    ],
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <TooltipProvider>
      <RouterProvider router={router} />
    </TooltipProvider>
  </StrictMode>,
);
