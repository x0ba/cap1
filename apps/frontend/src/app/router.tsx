import { createBrowserRouter } from "react-router-dom";
import App from "@/app/App";
import { AccountsPage } from "@/features/accounts/AccountsPage";
import { CardsPage } from "@/features/cards/CardsPage";
import { CreditWisePage } from "@/features/creditwise/CreditWisePage";
import { DepositsPage } from "@/features/deposits/DepositsPage";
import { HelpSupportPage } from "@/features/help-support/HelpSupportPage";
import { MoveMoneyPage } from "@/features/move-money/MoveMoneyPage";
import { OverviewPage } from "@/features/overview/OverviewPage";
import { PayBillsPage } from "@/features/pay-bills/PayBillsPage";
import { RewardsPage } from "@/features/rewards/RewardsPage";
import { SavingsGoalsPage } from "@/features/savings-goals/SavingsGoalsPage";
import { SettingsPage } from "@/features/settings/SettingsPage";
import { SpendingInsightsPage } from "@/features/spending-insights/SpendingInsightsPage";

export const router = createBrowserRouter([
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
